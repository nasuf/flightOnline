package com.flight.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.flight.model.Ticket;

public interface TicketRepository extends MongoRepository<Ticket, String> {

	Page<Ticket> findAll(Pageable page);
	
	Page<Ticket> findByPublisher(String publisher, Pageable page);
	
	Page<Ticket> findByIsDeleted(Boolean isDeleted, Pageable page);
	
}
