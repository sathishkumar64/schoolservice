package com.schoolservice.repositories;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.schoolservice.domain.School;
import com.schoolservice.domain.SchoolAppData;

public interface SchoolRepository extends Repository<School, Long> {	
	
	SchoolAppData save(School school);
	
	List<School> findAll();
	
	School findByschoolname(String schoolName);
}
