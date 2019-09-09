package com.schoolservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.schoolservice.domain.Ratings;
import com.schoolservice.domain.School;
import com.schoolservice.domain.SchoolAppData;
import com.schoolservice.domain.StudentAppData;
import com.schoolservice.repositories.MongoDBSchoolRepository;
import com.schoolservice.util.MessageConsumer;

@RestController
@RequestMapping(path="/api")
public class SchoolServiceController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());	
		
	@Value("${student.api.url:http://studentservice:8098/api/student}")
    private String remoteURL;
	
	@Value("${ratings.api.url:http://ratingservice:8080/ratings}")
    private String ratingServiceURL;
	
	@Autowired
	RestTemplate restTemplate;		
	
	@Autowired
	BuildProperties buildProperties;
	
	@Autowired
	public MessageConsumer messageConsumer;
	

	@Autowired
	public MongoDBSchoolRepository mongoDBSchoolRepository;	
	
		
	@GetMapping(path="/school/all")
    public @ResponseBody  List<School> retriveSchool(@RequestHeader HttpHeaders headers) {
		logger.info("Retrieving all School Details {} with headers.....",headers);
        List<School> schoolList = mongoDBSchoolRepository.findAll();
        System.out.println(schoolList.size());
        schoolList.forEach(school -> {
        	school.setRating(getSchoolRating(school.getSchoolId()));
        });
        return schoolList;
    }
	
	@GetMapping(path="/school")
    public @ResponseBody  School retriveSchoolByName(@RequestParam (value = "schoolname") String schoolname) {
		logger.info("Retrieving School Details By Name {}....................",schoolname);
        School school = mongoDBSchoolRepository.findByschoolname(schoolname);
        return school;
    }
	
	
	@PostMapping(path="/school/create" )
	public SchoolAppData saveSchool(@RequestBody School school) {	
			logger.info("Posting School detail ................");			
			SchoolAppData appData=null;		
			appData=mongoDBSchoolRepository.save(school);				
			 String buildInfo=setBuildInfo();		
			appData.setSchoolAppInfo(buildInfo);
			appData.setSchoolName(school.getSchoolname());
			appData.setMessage("School data successuflly saved.");	
		return appData;
	}
	
	
	

	@RequestMapping(value = "/school/getStudentsBySchool/{schoolname}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public SchoolAppData getStudents(@PathVariable String schoolname,@RequestHeader HttpHeaders headers) {	
		logger.info("Reading Header Info from School Service::::::::: {}",headers);	
		logger.info("Getting School details for {}/getStudentDetailsForSchool {}", remoteURL,schoolname);					
		HttpEntity<?> entity = new HttpEntity(headers);						
		SchoolAppData schoolData=findStudent(schoolname,entity,remoteURL);		
		return schoolData;		
		
	}

	
	private String setBuildInfo(){		
		StringBuilder builder=new StringBuilder();
		builder.append("School Application Name :" + buildProperties.getName() +" - Version: " + buildProperties.getVersion());		
		return builder.toString();
	}


	
	
	public SchoolAppData findStudent(String schoolName,HttpEntity<?> entity,String remoteURL) {		
		SchoolAppData appData=new SchoolAppData();		
		StudentAppData response = restTemplate.exchange(remoteURL+"/getStudentDetailsForSchool/{schoolname}", HttpMethod.GET, entity, new ParameterizedTypeReference<StudentAppData>() {
		}, schoolName).getBody();	
		logger.info("Response received from student service as {}. " , response.toString());		
		String jmsMessage=messageConsumer.getMessage();		
		logger.info("JMS message consumed from student service as {}. " , jmsMessage);		
		String buildInfo=setBuildInfo();		
		appData.setSchoolAppInfo(buildInfo);
		appData.setSchoolName(schoolName);
		appData.setStudentInfo(response);	
		appData.setJmsMessage("Message read from Student Active MQueue :"+jmsMessage);
		logger.info(appData.toString());
		return appData;
	}
	
	public float getSchoolRating(String schoolId) {
		HttpEntity<?> entity = new HttpEntity() {};	
		Ratings ratingsResponse  = restTemplate.exchange(ratingServiceURL +"/{schoolId}", HttpMethod.GET, entity, new ParameterizedTypeReference<Ratings>() {
		}, schoolId).getBody();
		logger.info("Response received from rating service as {}. " , ratingsResponse.toString());
		return ratingsResponse.getRating();
	}
	
	@Bean	
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
