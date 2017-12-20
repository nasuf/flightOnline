package com.flight.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.flight.model.User;

public interface UserRepository extends MongoRepository<User, String>{

	User findByOpenid(String openid);

}
