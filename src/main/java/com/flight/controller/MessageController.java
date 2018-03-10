package com.flight.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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

import com.flight.model.Custom;
import com.flight.model.Message;
import com.flight.model.Question;
import com.flight.model.Ticket;
import com.flight.model.Tourism;
import com.flight.model.User;
import com.flight.repository.CustomRepository;
import com.flight.repository.MessageRepository;
import com.flight.repository.QuestionRepository;
import com.flight.repository.TicketRepository;
import com.flight.repository.TourismRepository;
import com.flight.repository.UserRepository;
import com.flight.utils.Constant;
import com.flight.utils.HttpResult;
import com.flight.utils.MongoUtils;

@Controller
@RequestMapping("/message")
public class MessageController {
	
	@Autowired
	private MessageRepository msgRepository;
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	@RequestMapping(value = "/message", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> postMessage(@RequestBody Message message) {
		if (null != message) {
			// save new message
			message.setIsDeleted(false);
			message.setIsSticky(false);
			message.setPostDate(new Date().getTime());
			Message savedMessage = this.messageRepository.save(message);
			if (null != savedMessage) {
				logger.info("User [" + savedMessage.getPosterNickName() + "] posted a message: [" + savedMessage.getContent() + "] on TOPIC:[" + savedMessage.getBeRepliedTopicTitle() + "]");
				/*// update poster info
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
					logger.info("Update user [ " + updatedPoster.getId() + " ] message list successfully.");*/
				
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
				} else if (savedMessage.getSubject().equals(Constant.MESSAGE_SUBJECT_CUSTOM)) {
					Custom beRepliedCustom = this.customRepository.findOne(savedMessage.getBeRepliedTopicId());
					List<String> replyList = beRepliedCustom.getReplyList();
					if (null == replyList) 
						replyList = new ArrayList<String> ();
					replyList.add(savedMessage.getId());
					beRepliedCustom.setReplyList(replyList);
					beRepliedCustom.setReplyCnt(null == beRepliedCustom.getReplyCnt() ? 1 : beRepliedCustom.getReplyCnt() + 1);
					Custom updatedBeRepliedCustom = this.customRepository.save(beRepliedCustom);
					if (null != updatedBeRepliedCustom) 
						logger.info("Update message [ " + updatedBeRepliedCustom.getId() + " ] reply list successfully.");
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

	@RequestMapping(value="/replies", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> findReplies(
			@RequestParam("beRepliedTopicId") String beRepliedTopicId, 
			@RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "subject") String subject,
			@RequestParam(value = "sort", required = false) String sort) {
		Boolean checkBestAns = false;
		String bestAnsId = null;
		if (StringUtils.isNotEmpty(subject) && subject.equals(Constant.MESSAGE_SUBJECT_QUESTION)) {
			Question foundQuestion = this.questionRepository.findOne(beRepliedTopicId);
			if (foundQuestion.getIsFixed() == Constant.QUESTION_FIXED && foundQuestion.getBestAnswer() != null) {
				checkBestAns = true;
				bestAnsId = foundQuestion.getBestAnswer();
			}
		}
		Page<Message> repliesPage = this.msgRepository.findByBeRepliedTopicId(beRepliedTopicId, new PageRequest(pageNumber, pageSize, MongoUtils.buildSort(sort)));
		List<Message> contents = repliesPage.getContent();
		List<Map<String, Object>> replies = new ArrayList<Map<String, Object>> ();
		if (null != contents && !contents.isEmpty()) {
			for (Message reply: contents) {
				String beRepliedMessageId = reply.getBeRepliedMessageId();
				Map<String, Object> data = new HashMap<String, Object>();
				String beRepliedPosterId = reply.getBeRepliedPoster();
				if (null != beRepliedPosterId) {
					User foundBeRepliedPoster = this.userRepository.findByOpenid(beRepliedPosterId);
					reply.setBeRepliedPoster(foundBeRepliedPoster.getNickName());
				}
				data.put("replyMessage", reply);
				if (null != beRepliedMessageId) {
					Message beRepliedMessage = this.msgRepository.findOne(beRepliedMessageId);
					if (null != beRepliedMessage) {
						if (null != beRepliedMessage.getIsDeleted() && beRepliedMessage.getIsDeleted() == true) {
							data.put("repliedContent", "【该评论已被删除】");
						} else {
							String content = beRepliedMessage.getContent();
							data.put("repliedContent", content);
						}
					}
				}
				if (checkBestAns == true && StringUtils.isNotEmpty(bestAnsId) && bestAnsId.equals(reply.getId())) {
					replies.add(0, data);
				} else {
					replies.add(data);
				}
			}
		}
		return new ResponseEntity<Map<String, Object>>(new HttpResult(Constant.RESULT_STATUS_SUCCESS,
				"Replies found successfully.", replies).build(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/reply/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Map<String, Object>> deleteReply(@PathVariable("id") String id) {
		Message foundMessage = this.msgRepository.findOne(id);
		if (null != foundMessage){
			foundMessage.setIsDeleted(true);
			foundMessage.setDeletedDate(new Date().getTime());
			Message updatedMessage = this.msgRepository.save(foundMessage);
			if (null != updatedMessage) {
				logger.info("Set message [" + updatedMessage.getId() + "] as DELETED successfully.");
				return new ResponseEntity<Map<String, Object>>(new HttpResult(Constant.RESULT_STATUS_SUCCESS,
						"Reply updated into DELETED successfully.").build(), HttpStatus.OK);
			}
		}
		return null;
	}
	
	@RequestMapping(value = "/bestAnswer", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> setBestAnswer(@RequestParam("questionId") String questionId,
			@RequestParam("msgId") String msgId) {
		if (null != questionId && null != msgId) {
			Question foundQuestion = this.questionRepository.findOne(questionId);
			if (null != foundQuestion) {
				foundQuestion.setBestAnswer(msgId);
				foundQuestion.setIsFixed(Constant.QUESTION_FIXED);
				Question updatedQuestion = this.questionRepository.save(foundQuestion);
				if (null != updatedQuestion) {
					logger.info("Set question [" + updatedQuestion.getId() + "] to be fixed successfully.");
					return new ResponseEntity<Map<String, Object>>(new HttpResult(Constant.RESULT_STATUS_SUCCESS,
							"Set question " + questionId + " to be fixed successfully.").build(), HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<Map<String, Object>>(new HttpResult(Constant.RESULT_STATUS_FAILURE,
				"Set question " + questionId + " to be fixed failed.").build(), HttpStatus.OK);
	}
	
}
