package com.flight.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.flight.model.User;

public interface UserRepository extends MongoRepository<User, String>{

	User findByOpenid(String openid);
	
	Page<User> findByRole(String role, Pageable page);

}
