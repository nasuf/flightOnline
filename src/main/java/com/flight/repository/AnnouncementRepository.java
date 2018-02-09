package com.flight.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.flight.model.Announcement;

public interface AnnouncementRepository extends MongoRepository<Announcement, String> {

}
