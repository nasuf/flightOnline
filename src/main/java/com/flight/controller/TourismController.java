package com.flight.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.flight.model.Tourism;
import com.flight.repository.TourismRepository;
import com.flight.utils.Constant;
import com.flight.utils.DateUtils;
import com.flight.utils.HttpResult;

@Controller
@RequestMapping("tourism")
public class TourismController {
	
	@Autowired
	private TourismRepository tourismRepository;
	
	@RequestMapping(value = "/publish/tourism", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> publish(@RequestParam("openid") String openid, @RequestBody Tourism tourism) {
		if (null != tourism) {
			Date now = new Date();
			tourism.setIsDeleted(false);
			tourism.setIsEnded(false);
			tourism.setIsFull(false);
			tourism.setPostDate(DateUtils.formatWithTimeZone(now));
			tourism.setPostWeekOfYear(DateUtils.getWeekOfYear(now));
			tourism.setPoster(openid);
			tourism.setReplyCnt(0);
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
		List<Tourism> tourismList = tourismPage.getContent();
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Find all tourism data successfully", tourismList).build(),
				HttpStatus.OK);
		
	}
	
}
