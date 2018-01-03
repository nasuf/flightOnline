package com.flight.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.flight.model.Ticket;
import com.flight.model.User;
import com.flight.repository.TicketRepository;
import com.flight.repository.UserRepository;
import com.flight.utils.Constant;
import com.flight.utils.HttpResult;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value = "/publish/ticket", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> publishTicket(@RequestBody Ticket ticket, @RequestParam("openid") String openid) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
		
		if (null != ticket) {
			User publisher = this.userRepository.findByOpenid(openid);
			String publishDate = sdf.format(new Date());
			ticket.setPublisher(publisher.getOpenid());
			ticket.setPublishDate(publishDate);
			ticket.setIsDeleted(false);
			Ticket savedTicket = this.ticketRepository.save(ticket);
			if (null != savedTicket) {
				logger.info("Saved new ticket info successfully.");
				return new ResponseEntity<Map<String, Object>>(
						new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Publish new ticket info successfully.").build(),
						HttpStatus.OK);
			} else {
				logger.error("Save new ticket info failed. Ticket Details: [ " + ticket.toString() + " ]");
				return new ResponseEntity<Map<String, Object>>(
						new HttpResult(Constant.RESULT_STATUS_FAILURE, "Publish new ticket info successfully.").build(),
						HttpStatus.OK);
				
			}
		}
		return null;
		
	}
	
}
