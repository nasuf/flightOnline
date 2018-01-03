package com.flight.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.flight.model.Message;

public interface MessageRepository extends MongoRepository<Message, String> {
	
	Page<Message> findByBeRepliedTopicId(String beRepliedTopicId, Pageable page); 

}
