package com.flight.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flight.model.Custom;
import com.flight.model.Message;
import com.flight.model.PrivMessage;
import com.flight.model.Question;
import com.flight.model.Ticket;
import com.flight.model.Tourism;
import com.flight.model.User;
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
@RequestMapping("user")
public class UserController {
	
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
	private PrivMessageRepository privMsgRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(value = "topics/tickets", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Map<String, Object>> findUserTicketTopics(@RequestParam("openid") String openid,
			@RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "sort", required = false) String sort) {
		if (null != openid) {
			Page<Ticket> ticketPage = this.ticketRepository.findByPublisher(openid, new PageRequest(pageNumber, pageSize, MongoUtils.buildSort(sort)));
			List<Ticket> tickets = ticketPage.getContent();
			return new ResponseEntity<Map<String, Object>>(
					new HttpResult(Constant.RESULT_STATUS_SUCCESS,
							"Find user tickets records successfully.", tickets).build(),
					HttpStatus.OK);
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE,
						"Find user tickets records failed.").build(),
				HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "topics/tourisms", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Map<String, Object>> findUserTourismTopics(@RequestParam("openid") String openid,
			@RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "sort", required = false) String sort) {
		if (null != openid) {
			Page<Tourism> tourismPage = this.tourismRepository.findByPoster(openid, new PageRequest(pageNumber, pageSize, MongoUtils.buildSort(sort)));
			List<Tourism> tourisms = tourismPage.getContent();
			return new ResponseEntity<Map<String, Object>>(
					new HttpResult(Constant.RESULT_STATUS_SUCCESS,
							"Find user tourisms records successfully.", tourisms).build(),
					HttpStatus.OK);
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE,
						"Find user tourisms records failed.").build(),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "topics/questions", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Map<String, Object>> findUserQuestionTopics(@RequestParam("openid") String openid,
			@RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "sort", required = false) String sort) {
		if (null != openid) {
			Page<Question> questionPage = this.questionRepository.findByPoster(openid, new PageRequest(pageNumber, pageSize, MongoUtils.buildSort(sort)));
			List<Question> questions = questionPage.getContent();
			return new ResponseEntity<Map<String, Object>>(
					new HttpResult(Constant.RESULT_STATUS_SUCCESS,
							"Find user questions records successfully.", questions).build(),
					HttpStatus.OK);
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE,
						"Find user questions records failed.").build(),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "topics/customs", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Map<String, Object>> findUserCustomTopics(@RequestParam("openid") String openid,
			@RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "sort", required = false) String sort) {
		if (null != openid) {
			Page<Custom> customPage = this.customRepository.findByPoster(openid, new PageRequest(pageNumber, pageSize, MongoUtils.buildSort(sort)));
			List<Custom> customs = customPage.getContent();
			return new ResponseEntity<Map<String, Object>>(
					new HttpResult(Constant.RESULT_STATUS_SUCCESS,
							"Find user customs records successfully.", customs).build(),
					HttpStatus.OK);
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE,
						"Find user customs records failed.").build(),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "comments/sent", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Map<String, Object>> findCommentsSent(@RequestParam("openid") String openid,
			@RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "sort", required = false) String sort) {
		if (null != openid) {
			Page<Message> msgPage = this.messageRepository.findByPoster(openid, new PageRequest(pageNumber, pageSize, MongoUtils.buildSort(sort)));
			List<Message> messages = msgPage.getContent();
			return new ResponseEntity<Map<String, Object>>(
					new HttpResult(Constant.RESULT_STATUS_SUCCESS,
							"Find user message sent records successfully.", messages).build(),
					HttpStatus.OK);
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE,
						"Find user message sent records failed.").build(),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "comments/received", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Map<String, Object>> findCommentsReceived(@RequestParam("openid") String openid,
			@RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "sort", required = false) String sort) {
		if (null != openid) {
			Page<Message> msgPage = this.messageRepository.findByBeRepliedPoster(openid, new PageRequest(pageNumber, pageSize, MongoUtils.buildSort(sort)));
			List<Message> messages = msgPage.getContent();
			return new ResponseEntity<Map<String, Object>>(
					new HttpResult(Constant.RESULT_STATUS_SUCCESS,
							"Find user message received records successfully.", messages).build(),
					HttpStatus.OK);
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE,
						"Find user message received records failed.").build(),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Map<String, Object>> findUser(@RequestParam("openid") String openid) {
		if (null != openid) {
			User foundUser = this.userRepository.findByOpenid(openid);
			List<PrivMessage> privMessageList = foundUser.getPrivMessageList();
			if (null != privMessageList && privMessageList.size() != 0) {
				Collections.sort(privMessageList, new Comparator<PrivMessage>() {  
					@Override
					public int compare(PrivMessage m1, PrivMessage m2) {
						return m1.getPostDate() < m2.getPostDate() ? 1 : -1;
					}  
		        });
			}
			List<Map<String,Object>> signUpTourismList = foundUser.getSignUpTourismList();
			
			if (null != signUpTourismList && signUpTourismList.size() != 0) {
				Collections.sort(signUpTourismList, new Comparator<Map<String, Object>>(){
					@Override
					public int compare(Map<String, Object> m1, Map<String, Object> m2) {
						return (Long)m1.get("date") < (Long)m2.get("date") ? 1 : -1;
					}
				});
			}
			foundUser.setPrivMessageList(privMessageList);
			return new ResponseEntity<Map<String, Object>>(
					new HttpResult(Constant.RESULT_STATUS_SUCCESS,
							"Find user successfully.", foundUser).build(),
					HttpStatus.OK);
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE,
						"Find user successfully.").build(),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sent/privmsg", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> sendPrivMsgToAdmin(@RequestBody PrivMessage msg) {
		if (null != msg) {
			User foundUser = this.userRepository.findByOpenid(msg.getPoster());
			msg.setPostDate(new Date().getTime());
			msg.setPosterNickName(foundUser.getNickName());
			PrivMessage savedPrivMsg = this.privMsgRepository.save(msg);
			if (null != savedPrivMsg) {
				List<PrivMessage> list = foundUser.getPrivMessageList();
				if (null == list) 
					list = new ArrayList<PrivMessage>();
				list.add(savedPrivMsg);
				foundUser.setPrivMessageList(list);
				foundUser.setWechatId(msg.getWechatId());
				User updatedUser = this.userRepository.save(foundUser);
				if (null != updatedUser && null != savedPrivMsg) {
					return new ResponseEntity<Map<String, Object>>(
							new HttpResult(Constant.RESULT_STATUS_SUCCESS,
									"Sent private message to admin successfully.").build(),
							HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE,
						"Sent private message to admin failed.").build(),
				HttpStatus.OK);
	}
	
}
