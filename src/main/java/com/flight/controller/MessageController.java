package com.flight.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.flight.model.Message;
import com.flight.model.Question;
import com.flight.model.User;
import com.flight.repository.MessageRepository;
import com.flight.repository.QuestionRepository;
import com.flight.repository.UserRepository;
import com.flight.utils.Constant;
import com.flight.utils.HttpResult;

@Controller
@RequestMapping("/message")
public class MessageController {
	
	@Autowired
	private MessageRepository msgRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private QuestionRepository questionRepository;

	@RequestMapping(value="/replies", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> findReplies(
			@RequestParam("beRepliedTopicId") String beRepliedTopicId, 
			@RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "subject") String subject) {
		Boolean checkBestAns = false;
		String bestAnsId = null;
		if (StringUtils.isNotEmpty(subject) && subject.equals(Constant.MESSAGE_SUBJECT_QUESTION)) {
			Question foundQuestion = this.questionRepository.findOne(beRepliedTopicId);
			if (foundQuestion.getIsFixed() == true && foundQuestion.getBestAnswer() != null) {
				checkBestAns = true;
				bestAnsId = foundQuestion.getBestAnswer();
			}
		}
		Page<Message> repliesPage = this.msgRepository.findByBeRepliedTopicId(beRepliedTopicId, new PageRequest(pageNumber, pageSize));
		List<Message> contents = repliesPage.getContent();
		List<Map<String, Object>> replies = new ArrayList<Map<String, Object>> ();
		if (null != contents || !contents.isEmpty()) {
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
			Message updatedMessage = this.msgRepository.save(foundMessage);
			if (null != updatedMessage) {
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
				foundQuestion.setIsFixed(true);
				Question updatedQuestion = this.questionRepository.save(foundQuestion);
				if (null != updatedQuestion) {
					return new ResponseEntity<Map<String, Object>>(new HttpResult(Constant.RESULT_STATUS_SUCCESS,
							"Set question " + questionId + " to be fixed successfully.").build(), HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<Map<String, Object>>(new HttpResult(Constant.RESULT_STATUS_FAILURE,
				"Set question " + questionId + " to be fixed failed.").build(), HttpStatus.OK);
	}
	
}
