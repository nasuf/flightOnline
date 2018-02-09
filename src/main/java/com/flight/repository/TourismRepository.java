package com.flight.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.flight.model.Question;
import com.flight.model.Ticket;
import com.flight.model.Tourism;

public interface TourismRepository extends MongoRepository<Tourism, String> {

	Page<Tourism> findAll(Pageable page);
	
	Page<Tourism> findByPoster(String poster, Pageable page);
	
	Page<Tourism> findByIsDeleted(Boolean isDeleted, Pageable page);
	
}
