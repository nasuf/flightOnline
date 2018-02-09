package com.flight.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.flight.model.Message;
import com.flight.model.Question;
import com.flight.model.Ticket;

public interface QuestionRepository extends MongoRepository<Question, String> {

	Page<Question> findAll(Pageable page);
	
	Page<Question> findByTitleLikeIgnoreCase(String keywords, Pageable page);
	
	Page<Question> findByContentLikeIgnoreCase(String keywords, Pageable page);
	
	Page<Question> findByPoster(String poster, Pageable page);
	
	Page<Question> findByIsDeleted(Boolean isDeleted, Pageable page);

}
