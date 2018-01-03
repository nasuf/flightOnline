package com.flight.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flight.model.Ticket;
import com.flight.repository.TicketRepository;
import com.flight.utils.Constant;
import com.flight.utils.HttpResult;

@Controller
@RequestMapping("/tickets")
public class TicketController {

	@Autowired
	private TicketRepository ticketRepository;

	private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

	@RequestMapping(value = "/tickets")
	public ResponseEntity<Map<String, Object>> findAllTickets() {

		List<Ticket> tickets = this.ticketRepository.findAll();
		Map<String, Object> data = new HashMap<String, Object>();
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Find all tickets data successfully", tickets).build(),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/ticket/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<Map<String, Object>> deleteTicket(@PathVariable("id") String id) {
		if (null != id) {
			Ticket foundTicket = this.ticketRepository.findOne(id);
			if (null != foundTicket) {
				foundTicket.setIsDeleted(true);
				Ticket updatedTicket = this.ticketRepository.save(foundTicket);
				if (null != updatedTicket) {
					logger.info("Set ticket " + foundTicket.getId() + " deleted as [true]  successfully.");
					return new ResponseEntity<Map<String, Object>>(
							new HttpResult(Constant.RESULT_STATUS_SUCCESS,
									"Set ticket " + foundTicket.getId() + " deleted as [true]  successfully.").build(),
							HttpStatus.OK);
				}
			}
		}
		return null;
	}

	@RequestMapping(value = "/ticket/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Map<String, Object>> findTicket(@PathVariable("id") String id) {
		if (null != id) {
			Ticket foundTicket = this.ticketRepository.findOne(id);
			if (null != foundTicket) {
				logger.info("Found ticket [ " + foundTicket.getId() + " ]" );
				return new ResponseEntity<Map<String, Object>>(
						new HttpResult(Constant.RESULT_STATUS_SUCCESS,
								"Found ticket [ " + foundTicket.getId() + " ].", foundTicket).build(),
						HttpStatus.OK);
			}
		}
		return null;
	}
	

}
