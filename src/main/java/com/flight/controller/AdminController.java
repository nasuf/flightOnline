package com.flight.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.flight.model.Announcement;
import com.flight.model.Custom;
import com.flight.model.Message;
import com.flight.model.PrivMessage;
import com.flight.model.Question;
import com.flight.model.Ticket;
import com.flight.model.Tourism;
import com.flight.model.User;
import com.flight.repository.AnnouncementRepository;
import com.flight.repository.CustomRepository;
import com.flight.repository.MessageRepository;
import com.flight.repository.PrivMessageRepository;
import com.flight.repository.QuestionRepository;
import com.flight.repository.TicketRepository;
import com.flight.repository.TourismRepository;
import com.flight.repository.UserRepository;
import com.flight.utils.Constant;
import com.flight.utils.HttpResult;
import com.flight.utils.MongoUtils;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private TourismRepository tourismRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private CustomRepository customRepository;
	
	@Autowired
	private AnnouncementRepository annoRepository;
	
	@Autowired
	private PrivMessageRepository privMsgRepository;
	
	@RequestMapping(value = "validate/password", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> validatePwd(@RequestParam("pwd") String pwd, @RequestParam("openid") String openid) {
		if (null != pwd && null != openid) {
			User foundAdmin = this.userRepository.findByOpenid(openid);
			if (null != foundAdmin && foundAdmin.getAdminPwd().equals(pwd)) {
				logger.info(foundAdmin.getNickName() + " validated admin password! ");
				return new ResponseEntity<Map<String, Object>>(
						new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Admin password validated successfully", true).build(),
						HttpStatus.OK);
			}
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Admin password validated failed", false).build(),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/password", method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> updatePassword(@RequestBody String newPwd, @RequestParam("openid") String openid) {
		if (null != newPwd && null != openid) {
			User foundAdmin = this.userRepository.findByOpenid(openid);
			if (null != foundAdmin) {
				foundAdmin.setAdminPwd(newPwd);
				User savedAdmin = this.userRepository.save(foundAdmin);
				if (null != savedAdmin) {
					logger.info(savedAdmin.getNickName() + " updated admin password successfully.");
					return new ResponseEntity<Map<String, Object>>(
							new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Admin password updated successfully", true).build(),
							HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Admin password updated failed", false).build(),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> findVips(
			@RequestParam(value = "role") String role,
			@RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "sort", required = false) String sort) {
		Page<User> userPage = this.userRepository.findByRole(role, new PageRequest(pageNumber, pageSize, MongoUtils.buildSort(sort)));
		List<User> users = userPage.getContent();
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Find vips successfully", users).build(),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/access", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> grantAccess(@RequestParam("openid") String openid, @RequestParam("value") Boolean value) {
		if (null != openid) {
			User foundUser = this.userRepository.findByOpenid(openid);
			foundUser.setRole(value==false ? Constant.USER_ROLE_USER : Constant.USER_ROLE_VIP);
			User savedUser = this.userRepository.save(foundUser);
			if (null != savedUser) {
				logger.info("Grant user [NickName: " + savedUser.getNickName() + ", Openid: " + savedUser.getOpenid() + "] " + 
						(value == false ? "USER role" : "VIP role successfully."));
				return new ResponseEntity<Map<String, Object>>(
						new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Grant access to user successfully.").build(),
						HttpStatus.OK);
			}
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE, "Grant access to user failed.").build(),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/topics/deleted", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> findDeletedTopics(@RequestParam("subject") String subject,
			@RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "sort", required = false) String sort) {
		if (subject.equals(Constant.MESSAGE_SUBJECT_TICKET)) {
			Page<Ticket> ticketPage = this.ticketRepository.findByIsDeleted(true, new PageRequest(pageNumber, pageSize, MongoUtils.buildSort(sort)));
			List<Ticket> deletedTickets = ticketPage.getContent();
			return new ResponseEntity<Map<String, Object>>(
					new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Find deleted tickets successfully.", deletedTickets).build(),
					HttpStatus.OK);
		} else if (subject.equals(Constant.MESSAGE_SUBJECT_CUSTOM)) {
			Page<Custom> customPage = this.customRepository.findByIsDeleted(true, new PageRequest(pageNumber, pageSize, MongoUtils.buildSort(sort)));
			List<Custom> deletedCustoms = customPage.getContent();
			return new ResponseEntity<Map<String, Object>>(
					new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Find deleted customs successfully.", deletedCustoms).build(),
					HttpStatus.OK);
		} else if (subject.equals(Constant.MESSAGE_SUBJECT_QUESTION)) {
			Page<Question> questionPage = this.questionRepository.findByIsDeleted(true, new PageRequest(pageNumber, pageSize, MongoUtils.buildSort(sort)));
			List<Question> deletedQuestions = questionPage.getContent();
			return new ResponseEntity<Map<String, Object>>(
					new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Find deleted questions successfully.", deletedQuestions).build(),
					HttpStatus.OK);
		} else if (subject.equals(Constant.MESSAGE_SUBJECT_TOURISM)) {
			Page<Tourism> tourismPage = this.tourismRepository.findByIsDeleted(true, new PageRequest(pageNumber, pageSize, MongoUtils.buildSort(sort)));
			List<Tourism> deletedTourisms = tourismPage.getContent();
			return new ResponseEntity<Map<String, Object>>(
					new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Find deleted tourisms successfully.", deletedTourisms).build(),
					HttpStatus.OK);
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE, "Find deleted topics failed.").build(),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/topic/recover/deleted", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> recoverDeletedTopics(@RequestParam("subject") String subject,
			@RequestParam("id") String id) {
		if (subject.equals(Constant.MESSAGE_SUBJECT_TICKET)) {
			Ticket deletedTicket = this.ticketRepository.findOne(id);
			deletedTicket.setIsDeleted(false);
			Ticket updatedTicket = this.ticketRepository.save(deletedTicket);
			if (null != updatedTicket) {
				logger.info("Recover deleted ticket: [Title: " + deletedTicket.getTitle() + ", id: " + 
						deletedTicket.getId() + "] successfully.");
			}
			return new ResponseEntity<Map<String, Object>>(
					new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Recover deleted ticket successfully.").build(),
					HttpStatus.OK);
		} else if (subject.equals(Constant.MESSAGE_SUBJECT_TOURISM)) {
			Tourism deletedTourism = this.tourismRepository.findOne(id);
			deletedTourism.setIsDeleted(false);
			Tourism updatedTourism = this.tourismRepository.save(deletedTourism);
			if (null != updatedTourism) {
				logger.info("Recover deleted tourism: [Title: " + updatedTourism.getTitle() + ", id: " + 
						updatedTourism.getId() + "] successfully.");
			}
			return new ResponseEntity<Map<String, Object>>(
					new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Recover deleted tourism successfully.").build(),
					HttpStatus.OK);
		} else if (subject.equals(Constant.MESSAGE_SUBJECT_QUESTION)) {
			Question deletedQuestion = this.questionRepository.findOne(id);
			deletedQuestion.setIsDeleted(false);
			Question updatedQuestion = this.questionRepository.save(deletedQuestion);
			if (null != updatedQuestion) {
				logger.info("Recover deleted question: [Title: " + updatedQuestion.getTitle() + ", id: " + 
						updatedQuestion.getId() + "] successfully.");
			}
			return new ResponseEntity<Map<String, Object>>(
					new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Recover deleted question successfully.").build(),
					HttpStatus.OK);
		} else if (subject.equals(Constant.MESSAGE_SUBJECT_CUSTOM)) {
			Custom deletedCustom = this.customRepository.findOne(id);
			deletedCustom.setIsDeleted(false);
			Custom updatedCustom = this.customRepository.save(deletedCustom);
			if (null != updatedCustom) {
				logger.info("Recover deleted custom: [Title: " + updatedCustom.getTitle() + ", id: " + 
						updatedCustom.getId() + "] successfully.");
			}
			return new ResponseEntity<Map<String, Object>>(
					new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Recover deleted custom successfully.").build(),
					HttpStatus.OK);
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE, "Recover deleted topic failed.").build(),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/messages/deleted", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> findDeletedMessages(@RequestParam("subject") String subject,
			@RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "sort", required = false) String sort) {
		Page<Message> messagePage = this.messageRepository.findBySubjectAndIsDeleted(subject, true, 
				new PageRequest(pageNumber, pageSize, MongoUtils.buildSort(sort)));
		List<Message> deletedMessages = messagePage.getContent();
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Find deleted messages successfully.", deletedMessages).build(),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/message/recover/deleted", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> recoverDeletedMessages(@RequestParam("id") String id) {
		if (null != id) {
			Message foundDeletedMsg = this.messageRepository.findOne(id);
			foundDeletedMsg.setIsDeleted(false);
			foundDeletedMsg.setDeletedDate(null);
			Message recoveredMessage = this.messageRepository.save(foundDeletedMsg);
			if (null != recoveredMessage) {
				logger.info("Recover deleted comment [id: " + recoveredMessage.getId() + ", content: " + recoveredMessage.getContent() + "] successfully.");
				return new ResponseEntity<Map<String, Object>>(
						new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Deleted message recovered successfully.").build(),
						HttpStatus.OK);
			}
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE, "Deleted message recovered failed.").build(),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/publish/announcement", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> publishAnnouncement(@RequestBody Announcement anno) {
		if (null != anno) {
			anno.setPostDate(new Date().getTime());
			Announcement savedAnno = this.annoRepository.save(anno);
			if (null != savedAnno) {
				logger.info("Publish new announcement successfully.");
				return new ResponseEntity<Map<String, Object>>(
						new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Publish Announcement successfully").build(),
						HttpStatus.OK);
			}
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE, "Publish Announcement failed").build(),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/privMessage", method = RequestMethod.GET )
	public ResponseEntity<Map<String, Object>> findUserPrivMessages(
			@RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "sort", required = false) String sort) {
		Page<PrivMessage> privMsgPage = this.privMsgRepository.findAll(
				new PageRequest(pageNumber, pageSize, MongoUtils.buildSort(sort)));
		if (null != privMsgPage) {
			List<PrivMessage> msgs = privMsgPage.getContent();
			return new ResponseEntity<Map<String, Object>>(
					new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Find private messages successfully", msgs).build(),
					HttpStatus.OK);
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE, "Find private messages failed").build(),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/switchRole", method = RequestMethod.GET )
	public ResponseEntity<Map<String, Object>> switchRole(@RequestParam("openid") String openid, @RequestParam("role") String role) {
		User user = this.userRepository.findByOpenid(openid);
		user.setRole(role);
		this.userRepository.save(user);
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_SUCCESS, "switch role successfully").build(),
				HttpStatus.OK);
	}
	
}
