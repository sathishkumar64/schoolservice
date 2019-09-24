package com.schoolservice.repositories;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.schoolservice.domain.Ratings;
import com.schoolservice.domain.SchoolAppData;
import com.schoolservice.domain.StudentAppData;

@Service
public class RestClientService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${student.api.url}")	
    private String remoteURL;
		
	@Value("${ratings.api.url}")
    private String ratingServiceURL;
	
	@Autowired
	RestTemplate restTemplate;	
	
	//@Autowired
	//public MessageConsumer messageConsumer;
	
	@Autowired
	BuildProperties buildProperties;
	
	
	
	public SchoolAppData findStudent(String schoolName,HttpEntity<?> entity) {		
		SchoolAppData appData=new SchoolAppData();		
		StudentAppData response =null;	
		 try {			
			 response = restTemplate.exchange(remoteURL+"/getStudentDetailsForSchool/{schoolname}", HttpMethod.GET, entity, new ParameterizedTypeReference<StudentAppData>() {
				}, schoolName).getBody();		 
		 } catch (ResourceAccessException ex) {			
		        throw new ResourceAccessException (ex.getLocalizedMessage());		       
		 }catch (HttpClientErrorException ex) {			
		     throw new IllegalArgumentException (ex.getResponseBodyAsString());		       
		 } 
		
		logger.info("Response received from student service as {}. " , response.toString());		
		//String jmsMessage=messageConsumer.getMessage();		
		//logger.info("JMS message consumed from student service as {}. " , jmsMessage);		
		String buildInfo=setBuildInfo();		
		appData.setSchoolAppInfo(buildInfo);
		appData.setSchoolName(schoolName);
		appData.setStudentInfo(response);	
	//	appData.setJmsMessage("Message read from Student Active MQueue :"+jmsMessage);
		logger.info(appData.toString());
		return appData;
	}
	
	

	private String setBuildInfo(){		
		StringBuilder builder=new StringBuilder();
		builder.append(buildProperties.getName() +" - Version: " + buildProperties.getVersion());		
		return builder.toString();
	}

	
	public float getSchoolRating(String schoolId) {
		HttpEntity<?> entity = new HttpEntity() {};	
		Ratings ratingsResponse  = restTemplate.exchange(ratingServiceURL +"/{schoolId}", HttpMethod.GET, entity, new ParameterizedTypeReference<Ratings>() {
		}, schoolId).getBody();		
		return ratingsResponse.getRating();
	}

	
	
	@Bean	
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
