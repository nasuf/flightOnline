package com.flight.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.flight.model.Ticket;

public interface TicketRepository extends MongoRepository<Ticket, String> {

	
	
}
