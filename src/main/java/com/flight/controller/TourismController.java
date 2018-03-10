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

import com.flight.model.Tourism;
import com.flight.model.User;
import com.flight.repository.TourismRepository;
import com.flight.repository.UserRepository;
import com.flight.utils.Constant;
import com.flight.utils.DateUtils;
import com.flight.utils.HttpResult;
import com.flight.utils.MongoUtils;

@Controller
@RequestMapping("tourism")
public class TourismController {

	@Autowired
	private TourismRepository tourismRepository;

	@Autowired
	private UserRepository userRepository;

	private static final Logger logger = LoggerFactory.getLogger(TourismController.class);

	@RequestMapping(value = "/publish/tourism", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> publish(@RequestParam("openid") String openid,
			@RequestBody Tourism tourism) {
		if (null != tourism) {
			User foundUser = this.userRepository.findByOpenid(openid);

			Date now = new Date();
			tourism.setIsDeleted(false);
			tourism.setIsEnded(false);
			tourism.setIsFull(false);
			tourism.setPostDate(now.getTime());
			tourism.setPostWeekOfYear(DateUtils.getWeekOfYear(now));
			tourism.setPoster(openid);
			tourism.setReplyCnt(0);
			Tourism savedTourism = this.tourismRepository.save(tourism);
			if (null != savedTourism) {
				logger.info("User [" + savedTourism.getPosterNickName() + "] posted a tourism topic [ " + savedTourism.getTitle() + " ] successfully.");
				return new ResponseEntity<Map<String, Object>>(
						new HttpResult(Constant.RESULT_STATUS_SUCCESS,
								"Save new tourism [ " + savedTourism.getId() + " ] successfully.").build(),
						HttpStatus.OK);
			}
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE, "Save new tourism failed.").build(), HttpStatus.OK);
	}

	@RequestMapping(value = "tourisms", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> findAll(
			@RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "sort", required = false) String sort) {

		Page<Tourism> tourismPage = this.tourismRepository
				.findAll(new PageRequest(pageNumber, pageSize, MongoUtils.buildSort(sort)));
		List<Tourism> tourismList = null;
		if (null != tourismPage) {
			tourismList = tourismPage.getContent();
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Find all tourism data successfully", tourismList)
						.build(),
				HttpStatus.OK);
	}

	@RequestMapping(value = "tourism", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> findTourism(@RequestParam("tourismId") String tourismId) {
		if (null != tourismId) {
			Tourism foundTourism = this.tourismRepository.findOne(tourismId);

			if (null != foundTourism) {
				return new ResponseEntity<Map<String, Object>>(
						new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Found tourism data successfully", foundTourism)
								.build(),
						HttpStatus.OK);
			}

		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE, "Found tourism data failed").build(), HttpStatus.OK);
	}

	@RequestMapping(value = "/tourism/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<Map<String, Object>> deleteTourism(@PathVariable("id") String id) {
		if (null != id) {
			Tourism foundTourism = this.tourismRepository.findOne(id);
			if (null != foundTourism) {
				foundTourism.setIsDeleted(true);
				foundTourism.setDeletedDate(new Date().getTime());
				Tourism updatedTourism = this.tourismRepository.save(foundTourism);
				if (null != updatedTourism) {
					logger.info("Set tourism " + updatedTourism.getId() + " deleted as [true]  successfully.");
					return new ResponseEntity<Map<String, Object>>(new HttpResult(Constant.RESULT_STATUS_SUCCESS,
							"Set tourism " + updatedTourism.getId() + " deleted as [true]  successfully.").build(),
							HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<Map<String, Object>>(new HttpResult(Constant.RESULT_STATUS_FAILURE,
				"Set tourism [" + id + "] isDeleted as [true]  successfully.").build(), HttpStatus.OK);
	}

	@RequestMapping(value = "tourism/signUp", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> tourismSignUp(@RequestParam("openid") String openid,
			@RequestParam("wechatId") String wechatId, @RequestParam("tourismId") String tourismId,
			@RequestParam("tourismPoster") String tourismPoster, @RequestParam("personalMsg") String personalMsg) {
		if (null == wechatId || null == openid) {
			return new ResponseEntity<Map<String, Object>>(
					new HttpResult(Constant.RESULT_STATUS_FAILURE, "WechatId or openid can't be null.").build(),
					HttpStatus.OK);
		} else {
			// 发信人
			User foundSentor = this.userRepository.findByOpenid(openid);
			// 收信人
			User foundReceiver = this.userRepository.findByOpenid(tourismPoster);
			// 发信/收信时间
			long date = new Date().getTime();
			// 更新出行 报名信息
			Tourism foundTourism = this.tourismRepository.findOne(tourismId);
			Set<Map<String, String>> signUpSet = foundTourism.getSignUpSet();
			if (null == signUpSet)
				signUpSet = new HashSet<Map<String, String>>();
			Map<String, String> map = new HashMap<String, String>();
			map.put("openid", openid);
			map.put("sentorNickName", foundSentor.getNickName());
			map.put("personalMsg", personalMsg);
			signUpSet.add(map);
			foundTourism.setSignUpSet(signUpSet);
			Tourism savedTourism = this.tourismRepository.save(foundTourism);

			// 更新发信人 私信
			List<Map<String, Object>> signUpTourismSentList = foundSentor.getSignUpTourismList();
			if (null == signUpTourismSentList)
				signUpTourismSentList = new ArrayList<Map<String, Object>>();
			Map<String, Object> signUpMapSent = new HashMap<String, Object>();
			signUpMapSent.put("type", "SENT");
			signUpMapSent.put("tourismId", tourismId);
			signUpMapSent.put("tourismTitle", savedTourism.getTitle());
			signUpMapSent.put("msg", personalMsg);
			signUpMapSent.put("receiverNickName", foundReceiver.getNickName());
			signUpMapSent.put("receiverOpenId", foundReceiver.getOpenid());
			signUpMapSent.put("date", date);
			signUpTourismSentList.add(signUpMapSent);
			foundSentor.setSignUpTourismList(signUpTourismSentList);
			foundSentor.setWechatId(wechatId);
			User savedSentor = this.userRepository.save(foundSentor);
			logger.info("User [NICKNAME:" + foundSentor.getNickName() + ", OPENID:"+foundSentor.getOpenid()+"] sent a signup message ["+signUpMapSent.toString()+"]");
			// 更新收信人 私信
			User foundNewReceiver = this.userRepository.findByOpenid(tourismPoster);
			List<Map<String, Object>> signUpTourismReceivedList = foundNewReceiver.getSignUpTourismList();
			if (null == signUpTourismReceivedList)
				signUpTourismReceivedList = new ArrayList<Map<String, Object>>();
			Map<String, Object> signUpMapReceived = new HashMap<String, Object>();
			signUpMapReceived.put("type", "RECEIVED");
			signUpMapReceived.put("tourismId", tourismId);
			signUpMapReceived.put("tourismTitle", savedTourism.getTitle());
			signUpMapReceived.put("msg", personalMsg);
			signUpMapReceived.put("wechatId", wechatId);
			signUpMapReceived.put("sentorNickName", foundSentor.getNickName());
			signUpMapReceived.put("sentorOpenId", foundSentor.getOpenid());
			signUpMapReceived.put("date", date);
			signUpTourismReceivedList.add(signUpMapReceived);
			foundNewReceiver.setSignUpTourismList(signUpTourismReceivedList);
			User savedReceiver = this.userRepository.save(foundNewReceiver);
			if (null != savedTourism && null != savedSentor && null != savedReceiver)
				return new ResponseEntity<Map<String, Object>>(
						new HttpResult(Constant.RESULT_STATUS_SUCCESS, "User [ " + savedSentor.getOpenid()
								+ " ] signUp tourism [ " + savedTourism.getId() + " ] " + "successfully.").build(),
						HttpStatus.OK);
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE, "Signup tourism failed.").build(), HttpStatus.OK);
	}

}
