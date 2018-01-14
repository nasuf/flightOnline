package com.flight.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.flight.model.Question;
import com.flight.repository.QuestionRepository;
import com.flight.utils.Constant;
import com.flight.utils.HttpResult;

@Controller
@RequestMapping("question")
public class QuestionController {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
		
	@RequestMapping(value = "questions", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> findAllQuestions(
			@RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber ) {
		Page<Question> questionsPage = this.questionRepository.findAll(new PageRequest(pageNumber, pageSize));
		
		List<Question> questions = questionsPage.getContent();
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Find all questions data successfully", questions).build(),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "question", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Map<String, Object>> findTicket(@RequestParam("questionId") String id) {
		if (null != id) {
			Question foundQuestion = this.questionRepository.findOne(id);
			if (null != foundQuestion) {
				logger.info("Found question [ " + foundQuestion.getId() + " ]" );
				return new ResponseEntity<Map<String, Object>>(
						new HttpResult(Constant.RESULT_STATUS_SUCCESS,
								"Found question [ " + foundQuestion.getId() + " ].", foundQuestion).build(),
						HttpStatus.OK);
			}
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE,
						"Found question failed.").build(),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/question/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<Map<String, Object>> deleteTicket(@PathVariable("id") String id) {
		if (null != id) {
			Question foundQuestion = this.questionRepository.findOne(id);
			if (null != foundQuestion) {
				foundQuestion.setIsDeleted(true);
				Question updatedQuestion = this.questionRepository.save(foundQuestion);
				if (null != updatedQuestion) {
					logger.info("Set question " + updatedQuestion.getId() + " deleted as [true]  successfully.");
					return new ResponseEntity<Map<String, Object>>(
							new HttpResult(Constant.RESULT_STATUS_SUCCESS,
									"Set ticket " + updatedQuestion.getId() + " deleted as [true]  successfully.").build(),
							HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE,
						"Set ticket deleted as [true]  successfully.").build(),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Map<String, Object>> search(@RequestParam("keywords") String keywords,
			@RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber) {
		if (null != keywords) {
			Page<Question> titleResultPage = this.questionRepository.findByTitleLikeIgnoreCase(keywords, new PageRequest(pageNumber, pageSize));
			List<Question> questions = new ArrayList<Question>();
			if (null != titleResultPage) {
				questions.addAll(titleResultPage.getContent());
			}
			Page<Question> contentResultPage = this.questionRepository.findByContentLikeIgnoreCase(keywords, new PageRequest(pageNumber, pageSize));
			if (null != contentResultPage) {
				questions.addAll(contentResultPage.getContent());
			}
			Set<Question> questionSet = new HashSet<Question> ();
			for (Question question: questions) {
				questionSet.add(question);
			}
			return new ResponseEntity<Map<String, Object>>(
					new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Find all question data successfully", questionSet).build(),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<Map<String, Object>>(
					new HttpResult(Constant.RESULT_STATUS_FAILURE, "Find all question data failed").build(),
					HttpStatus.OK);
		}
	}
	
}
