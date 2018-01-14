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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flight.model.Ticket;
import com.flight.model.Tourism;
import com.flight.model.User;
import com.flight.repository.TourismRepository;
import com.flight.repository.UserRepository;
import com.flight.utils.Constant;
import com.flight.utils.DateUtils;
import com.flight.utils.HttpResult;

@Controller
@RequestMapping("tourism")
public class TourismController {
	
	@Autowired
	private TourismRepository tourismRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(TourismController.class);
	
	@RequestMapping(value = "/publish/tourism", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> publish(@RequestParam("openid") String openid, @RequestBody Tourism tourism) {
		if (null != tourism) {
			User foundUser = this.userRepository.findByOpenid(openid);
			
			Date now = new Date();
			tourism.setIsDeleted(false);
			tourism.setIsEnded(false);
			tourism.setIsFull(false);
			tourism.setPostDate(DateUtils.formatWithTimeZone(now));
			tourism.setPostWeekOfYear(DateUtils.getWeekOfYear(now));
			tourism.setPoster(openid);
			tourism.setReplyCnt(0);
			tourism.setPosterAvatarUrl(foundUser.getAvatarUrl());
			Tourism savedTourism = this.tourismRepository.save(tourism);
			if (null != savedTourism) {
				return new ResponseEntity<Map<String, Object>>(
						new HttpResult(Constant.RESULT_STATUS_SUCCESS,
								"Save new tourism [ " + savedTourism.getId() + " ] successfully.").build(),
						HttpStatus.OK);
			}
		} 
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE,
						"Save new tourism failed.").build(),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "tourisms", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> findAll(
			@RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber) {
		
		Page<Tourism> tourismPage = this.tourismRepository.findAll(new PageRequest(pageNumber, pageSize));
		List<Tourism> tourismList = null;
		if (null != tourismPage) {
			tourismList = tourismPage.getContent();
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Find all tourism data successfully", tourismList).build(),
				HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "tourism", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> findTourism(@RequestParam("tourismId") String tourismId) {
		if (null != tourismId) {
			Tourism foundTourism = this.tourismRepository.findOne(tourismId);
			
			if (null != foundTourism) {
				User foundPoster = this.userRepository.findByOpenid(foundTourism.getPoster());
				foundTourism.setPoster(foundPoster.getNickName());
				return new ResponseEntity<Map<String, Object>>(
						new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Found tourism data successfully", foundTourism).build(),
						HttpStatus.OK);
			}
				
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE, "Found tourism data failed").build(),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/tourism/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<Map<String, Object>> deleteTourism(@PathVariable("id") String id) {
		if (null != id) {
			Tourism foundTourism = this.tourismRepository.findOne(id);
			if (null != foundTourism) {
				foundTourism.setIsDeleted(true);
				Tourism updatedTourism = this.tourismRepository.save(foundTourism);
				if (null != updatedTourism) {
					logger.info("Set tourism " + updatedTourism.getId() + " deleted as [true]  successfully.");
					return new ResponseEntity<Map<String, Object>>(
							new HttpResult(Constant.RESULT_STATUS_SUCCESS,
									"Set tourism " + updatedTourism.getId() + " deleted as [true]  successfully.").build(),
							HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE, 
						"Set tourism [" + id + "] isDeleted as [true]  successfully.").build(),
				HttpStatus.OK);
	}
	
}
