package com.flight.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.flight.model.Message;
import com.flight.model.Question;
import com.flight.model.Ticket;
import com.flight.model.Tourism;
import com.flight.model.User;
import com.flight.repository.MessageRepository;
import com.flight.repository.QuestionRepository;
import com.flight.repository.TicketRepository;
import com.flight.repository.TourismRepository;
import com.flight.repository.UserRepository;
import com.flight.utils.Constant;
import com.flight.utils.DateUtils;
import com.flight.utils.HttpResult;

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
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = "/message", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> postMessage(@RequestBody Message message) {
		if (null != message) {
			// save new message
			message.setIsDeleted(false);
			message.setIsSticky(false);
			message.setPostDate(DateUtils.formatWithTimeZone(new Date()));
			Message savedMessage = this.messageRepository.save(message);
			if (null != savedMessage) {
				
				// update poster info
				User foundPoster = this.userRepository.findByOpenid(message.getPoster());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(Constant.MESSAGE_SUBJECT, savedMessage.getSubject());
				map.put(Constant.MESSAGE_TYPE, savedMessage.getType());
				map.put(Constant.MESSAGE_ID, savedMessage.getId());
				map.put(Constant.MESSAGE_CONTENT, savedMessage.getContent());
				List<Map<String, Object>> list = foundPoster.getMessageList();
				if (null == list)
					list = new ArrayList<Map<String, Object>> ();
				list.add(map);
				foundPoster.setMessageList(list);
				User updatedPoster = this.userRepository.save(foundPoster);
				if (null != updatedPoster) 
					logger.info("Update user [ " + updatedPoster.getId() + " ] message list successfully.");
				
				// update topic reply list
				if (savedMessage.getSubject().equals(Constant.MESSAGE_SUBJECT_TICKET)) {
					Ticket beRepliedTicket = this.ticketRepository.findOne(savedMessage.getBeRepliedTopicId());
					List<String> replyList = beRepliedTicket.getReplyList();
					if (null == replyList) 
						replyList = new ArrayList<String> ();
					replyList.add(savedMessage.getId());
					beRepliedTicket.setReplyList(replyList);
					beRepliedTicket.setReplyCnt(null == beRepliedTicket.getReplyCnt() ? 1 : beRepliedTicket.getReplyCnt() + 1);
					Ticket updatedBeRepliedTicket = this.ticketRepository.save(beRepliedTicket);
					if (null != updatedBeRepliedTicket) 
						logger.info("Update message [ " + updatedBeRepliedTicket.getId() + " ] reply list successfully.");
					
				} else if (savedMessage.getSubject().equals(Constant.MESSAGE_SUBJECT_TOURISM)) {
					Tourism beRepliedTourism = this.tourismRepository.findOne(savedMessage.getBeRepliedTopicId());
					List<String> replyList = beRepliedTourism.getReplyList();
					if (null == replyList) 
						replyList = new ArrayList<String> ();
					replyList.add(savedMessage.getId());
					beRepliedTourism.setReplyList(replyList);
					beRepliedTourism.setReplyCnt(null == beRepliedTourism.getReplyCnt() ? 1 : beRepliedTourism.getReplyCnt() + 1);
					Tourism updatedBeRepliedTourism = this.tourismRepository.save(beRepliedTourism);
					if (null != updatedBeRepliedTourism) 
						logger.info("Update message [ " + updatedBeRepliedTourism.getId() + " ] reply list successfully.");
				} else if (savedMessage.getSubject().equals(Constant.MESSAGE_SUBJECT_QUESTION)) {
					Question beRepliedQuestion = this.questionRepository.findOne(savedMessage.getBeRepliedTopicId());
					List<String> replyList = beRepliedQuestion.getReplyList();
					if (null == replyList) 
						replyList = new ArrayList<String> ();
					replyList.add(savedMessage.getId());
					beRepliedQuestion.setReplyList(replyList);
					beRepliedQuestion.setReplyCnt(null == beRepliedQuestion.getReplyCnt() ? 1 : beRepliedQuestion.getReplyCnt() + 1);
					Question updatedBeRepliedQuestion = this.questionRepository.save(beRepliedQuestion);
					if (null != updatedBeRepliedQuestion) 
						logger.info("Update message [ " + updatedBeRepliedQuestion.getId() + " ] reply list successfully.");
				}
				
				// update beRepliedMessage
				if (savedMessage.getType().equals(Constant.MESSAGE_TYPE_REPLY) && null != savedMessage.getBeRepliedMessageId()) {
					Message beRepliedMessage = this.messageRepository.findOne(savedMessage.getBeRepliedMessageId());
					List<String> replyList_msg = savedMessage.getReplyList();
					if (null == replyList_msg)
						replyList_msg = new ArrayList<String> ();
					replyList_msg.add(savedMessage.getId());
					beRepliedMessage.setReplyList(replyList_msg);
					beRepliedMessage.setReplyCnt(null == beRepliedMessage.getReplyCnt() ? 1 : beRepliedMessage.getReplyCnt() + 1);
					Message updatedBeRepliedMessage = this.messageRepository.save(beRepliedMessage);
					if (null != updatedBeRepliedMessage) 
						logger.info("Update BeRepliedMessage [ " + updatedBeRepliedMessage.getId() + " ] successfully.");
				}
				
				
				return new ResponseEntity<Map<String, Object>>(
						new HttpResult(Constant.RESULT_STATUS_SUCCESS,
								"Save new message [ "+ savedMessage.getId() +" ] successfully.").build(),
						HttpStatus.OK);
			} else {
				return new ResponseEntity<Map<String, Object>>(
						new HttpResult(Constant.RESULT_STATUS_FAILURE,
								"Save new message [ "+ savedMessage.getId() +" ] failed.").build(),
						HttpStatus.OK);
			}
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE,
						"Save new message failed.").build(),
				HttpStatus.OK);
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
	
	@RequestMapping(value = "tourism/signUp", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> tourismSignUp(
			@RequestParam("openid") String openid, 
			@RequestParam("wechatId") String wechatId,
			@RequestParam("tourismId") String tourismId,
			@RequestParam("personalMsg") String personalMsg) {
		if (null == wechatId || null == openid) {
			return new ResponseEntity<Map<String, Object>>(
					new HttpResult(Constant.RESULT_STATUS_FAILURE,
							"WechatId or openid can't be null.").build(),
					HttpStatus.OK);
		} else {
			Tourism foundTourism = this.tourismRepository.findOne(tourismId);
			Set<Map<String, String>> signUpSet = foundTourism.getSignUpSet();
			if (null == signUpSet)
				signUpSet = new HashSet<Map<String, String>> ();
			Map<String, String> map = new HashMap<String, String>();
			map.put("openid", openid);
			map.put("personMsg", personalMsg);
			signUpSet.add(map);
			foundTourism.setSignUpSet(signUpSet);
			Tourism savedTourism = this.tourismRepository.save(foundTourism);
			User foundUser = this.userRepository.findByOpenid(openid);
			Set<String> signUpTourismList = foundUser.getSignUpTourismList();
			if (null == signUpTourismList) 
				signUpTourismList = new HashSet<String>();
			signUpTourismList.add(tourismId);
			foundUser.setSignUpTourismList(signUpTourismList);
			foundUser.setWechatId(wechatId);
			User savedUser = this.userRepository.save(foundUser);
			if (null != savedTourism && null != savedUser)
				return new ResponseEntity<Map<String, Object>>(
						new HttpResult(Constant.RESULT_STATUS_SUCCESS,
								"User [ " + savedUser.getOpenid() + " ] signUp tourism [ " + savedTourism.getId() + " ] "
										+ "successfully.").build(),
						HttpStatus.OK);
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE,
						"Signup tourism failed.").build(),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/question", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> raiseQuestion(@RequestBody Question question) {
		
		if (null != question) {
			question.setIsDeleted(false);
			question.setIsFixed(false);
			question.setPostDate(DateUtils.formatWithTimeZone(new Date()));
			question.setReplyCnt(0);
			Question savedQuestion = this.questionRepository.save(question);
			if (null != savedQuestion) {
				return new ResponseEntity<Map<String, Object>>(
						new HttpResult(Constant.RESULT_STATUS_SUCCESS,
								"Question [ " + savedQuestion.getId() + " ] raised successfully.").build(),
						HttpStatus.OK);
			}
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE,
						"Question raised failed.").build(),
				HttpStatus.OK);
	}
	
}
