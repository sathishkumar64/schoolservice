package com.schoolservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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

import com.schoolservice.domain.School;
import com.schoolservice.domain.SchoolAppData;
import com.schoolservice.repositories.MongoDBSchoolRepository;
import com.schoolservice.repositories.RestClientService;

@RestController
@RequestMapping(path="/api")
public class SchoolServiceController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());			

	@Autowired
	public MongoDBSchoolRepository mongoDBSchoolRepository;	
	
	@Autowired
	public RestClientService restClientService;
	
		
	@GetMapping(path="/school/all")
    public @ResponseBody  List<School> retriveSchool(@RequestHeader HttpHeaders headers) {
		logger.info("Retrieving all School Details {} with headers.....",headers);
        List<School> schoolList = mongoDBSchoolRepository.findAll();       
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
		return appData;
	}
	

	@RequestMapping(value = "/school/getStudentsBySchool/{schoolname}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public SchoolAppData getStudents(@PathVariable String schoolname,@RequestHeader HttpHeaders headers) {	
		logger.info("Reading Header Info from School Service::::::::: {}",headers);					
		HttpEntity<?> entity = new HttpEntity<>(headers);						
		SchoolAppData schoolData=restClientService.findStudent(schoolname,entity);		
		return schoolData;		
		
	}

}
