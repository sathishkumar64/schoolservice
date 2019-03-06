package com.schoolservice.repositories;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.schoolservice.domain.School;
import com.schoolservice.domain.SchoolAppData;


@Repository
public class MongoDBSchoolRepository implements SchoolRepository{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());	
	
	private final MongoOperations operations;
		
	
	@Autowired
	public MongoDBSchoolRepository(MongoOperations operations) {	
		this.operations = operations;
	}

	@Override
	public School findByschoolname(String schoolName) {
		Query query = query(where("schoolname").is(schoolName));
		return operations.findOne(query, School.class);
	}

	@Override
	public List<School> findAll() {	
		return operations.findAll(School.class);
	}

	@Override
	public SchoolAppData save(School school) {		
		SchoolAppData appData=new SchoolAppData();			
		operations.save(school);			
		return appData;
	}

}
