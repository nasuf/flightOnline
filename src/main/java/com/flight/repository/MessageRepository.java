package com.flight.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.flight.model.Message;

public interface MessageRepository extends MongoRepository<Message, String> {
	
	Page<Message> findByBeRepliedTopicId(String beRepliedTopicId, Pageable page); 
	
	Page<Message> findByPoster(String poster, Pageable page);
	
	Page<Message> findByBeRepliedPoster(String beRepliedPoster, Pageable page);
	
	Page<Message> findBySubjectAndIsDeleted(String subject, Boolean isDeleted, Pageable page);

	
}
