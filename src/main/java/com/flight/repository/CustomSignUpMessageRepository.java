package com.flight.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.flight.model.CustomSignUpMessage;

public interface CustomSignUpMessageRepository  extends MongoRepository<CustomSignUpMessage, String> {

}
