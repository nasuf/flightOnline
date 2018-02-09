package com.flight.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.flight.model.Custom;
import com.flight.model.Message;
import com.flight.model.Question;

public interface CustomRepository extends MongoRepository<Custom, String> {
	
	Page<Custom> findByType(String type, Pageable page);
	
	Page<Custom> findByPoster(String poster, Pageable page);
	
	Page<Custom> findByIsDeleted(Boolean isDeleted, Pageable page);

}
