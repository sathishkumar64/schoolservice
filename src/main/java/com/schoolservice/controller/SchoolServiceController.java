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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="COE School -Istio Implementation", description="Operations pertaining to School Service")
@RestController
@RequestMapping(path="/api")
public class SchoolServiceController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());			

	@Autowired
	public MongoDBSchoolRepository mongoDBSchoolRepository;	
	
	@Autowired
	public RestClientService restClientService;
	
	@ApiOperation(value = "View a list of available school", response = Iterable.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved list"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})	
	@GetMapping(path="/school/all")
    public @ResponseBody  List<School> retriveSchool(@RequestHeader HttpHeaders headers) {
		logger.info("Retrieving all School Details {} with headers.....",headers);
        List<School> schoolList = mongoDBSchoolRepository.findAll();
        schoolList.forEach(school -> {
        	school.setRating(restClientService.getSchoolRating(school.getSchoolId()));
        });
        return schoolList;      
    }
	
	@ApiOperation(value = "Search a school with an School Name",response = School.class)
	@GetMapping(path="/school")
    public @ResponseBody  School retriveSchoolByName(@RequestParam (value = "schoolname") String schoolname) {
		logger.info("Retrieving School Details By Name {}....................",schoolname);
        School school = mongoDBSchoolRepository.findByschoolname(schoolname);
        return school;
    }
	
	
	@ApiOperation(value = "Add a new School")
	@PostMapping(path="/school/create" )
	public SchoolAppData saveSchool(@RequestBody School school,@RequestHeader HttpHeaders headers) {	
			logger.info("Posting School detail {}................",headers);			
			SchoolAppData appData=null;		
			appData=mongoDBSchoolRepository.save(school);
		return appData;
	}
	

	@ApiOperation(value = "Search a Student List with an School Name",response = String.class)
	@RequestMapping(value = "/school/getStudentsBySchool/{schoolname}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public SchoolAppData getStudents(@PathVariable String schoolname,@RequestHeader HttpHeaders headers) {	
		logger.info("Reading Header Info from School Service::::::::: {}",headers);					
		HttpEntity<?> entity = new HttpEntity<>(headers);						
		SchoolAppData schoolData=restClientService.findStudent(schoolname,entity);		
		return schoolData;		
		
	}
	
	

}
