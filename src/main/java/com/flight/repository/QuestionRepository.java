package com.flight.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.flight.model.Question;

public interface QuestionRepository extends MongoRepository<Question, String> {

	Page<Question> findAll(Pageable page);
	
	Page<Question> findByTitleLikeIgnoreCase(String keywords, Pageable page);
	
	Page<Question> findByContentLikeIgnoreCase(String keywords, Pageable page);

}
