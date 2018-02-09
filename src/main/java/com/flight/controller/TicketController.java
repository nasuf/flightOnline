package com.flight.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flight.model.Announcement;
import com.flight.model.PrivMessage;
import com.flight.model.Ticket;
import com.flight.model.User;
import com.flight.repository.AnnouncementRepository;
import com.flight.repository.TicketRepository;
import com.flight.repository.UserRepository;
import com.flight.utils.Constant;
import com.flight.utils.DateUtils;
import com.flight.utils.HttpResult;
import com.flight.utils.MongoUtils;

@Controller
@RequestMapping("/tickets")
public class TicketController {

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AnnouncementRepository annoRepository;

	private static final Logger logger = LoggerFactory.getLogger(TicketController.class);
	
	@RequestMapping(value = "/publish/ticket", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> publishTicket(@RequestBody Ticket ticket, @RequestParam("openid") String openid) {
		
		if (null != ticket) {
			User publisher = this.userRepository.findByOpenid(openid);
			Date date = new Date();
			ticket.setPublisher(publisher.getOpenid());
			ticket.setPublishDate(date.getTime());
			ticket.setPublishWeekOfYear(DateUtils.getWeekOfYear(date));
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

	@RequestMapping(value = "/tickets")
	public ResponseEntity<Map<String, Object>> findAllTickets(
			@RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "sort", required = false) String sort) {

		Page<Ticket> ticketsPage = this.ticketRepository.findAll(new PageRequest(pageNumber, pageSize, MongoUtils.buildSort(sort)));
		List<Ticket> tickets = ticketsPage.getContent();
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
				foundTicket.setDeletedDate(new Date().getTime());
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
	
	@RequestMapping(value = "/oldMember/validate", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> oldMemberValidation(@RequestParam("wechatId") String wechatId, 
			@RequestParam("openid") String openid) {
		if (null == wechatId || null == openid) {
			return new ResponseEntity<Map<String, Object>>(
					new HttpResult(Constant.RESULT_STATUS_FAILURE,
							"WechatId or openid can't be null.").build(),
					HttpStatus.OK);
		} else {
			User foundUser = this.userRepository.findByOpenid(openid);
			if (null != foundUser) {
				foundUser.setWechatId(wechatId);
				User updatedUser = this.userRepository.save(foundUser);
				if (null != updatedUser)
					return new ResponseEntity<Map<String, Object>>(
							new HttpResult(Constant.RESULT_STATUS_SUCCESS,
									"Update wechatId to user [ " + updatedUser.getOpenid() + " ] successfully.").build(),
							
							HttpStatus.OK);
				else 
					return new ResponseEntity<Map<String, Object>>(
							new HttpResult(Constant.RESULT_STATUS_FAILURE,
									"Update wechatId to user [ " + updatedUser.getOpenid() + " ] failed.").build(),
							HttpStatus.OK);
			}
				
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE,
						"Update wechatId to user failed.").build(),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/announcement", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> loadAnnouncement() {
		List<Announcement> list = this.annoRepository.findAll();
		Collections.sort(list, new Comparator<Announcement>() {  
				@Override
				public int compare(Announcement m1, Announcement m2) {
					return m1.getPostDate() < m2.getPostDate() ? 1 : -1;
				}  
	        } );
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_SUCCESS,
						"Get latest announcement successfully.", list.get(0)).build(),
				HttpStatus.OK);
	}
	
	

}
