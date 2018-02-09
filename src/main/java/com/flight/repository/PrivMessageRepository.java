package com.flight.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.flight.model.PrivMessage;

public interface PrivMessageRepository extends MongoRepository<PrivMessage, String> {

}
