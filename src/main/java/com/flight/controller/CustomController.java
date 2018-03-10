package com.flight.controller;

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

import com.flight.model.Custom;
import com.flight.model.CustomSignUpMessage;
import com.flight.model.User;
import com.flight.repository.CustomRepository;
import com.flight.repository.CustomSignUpMessageRepository;
import com.flight.repository.UserRepository;
import com.flight.utils.Constant;
import com.flight.utils.HttpResult;
import com.flight.utils.MongoUtils;

@Controller
@RequestMapping("/custom")
public class CustomController {
	
	@Autowired
	private CustomRepository customRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CustomSignUpMessageRepository customSignUpMessageRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(CustomController.class);
	
	@RequestMapping(value = "/custom", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> publishCustom(@RequestBody Custom custom) {
		if (null != custom) {
			custom.setPostDate(new Date().getTime());
			custom.setIsDeleted(false);
			Custom savedCustom = this.customRepository.save(custom);
			if (null != savedCustom) {
				logger.info("User [ " + savedCustom.getPosterNickName() + " ] posted a custom topic [ " + savedCustom.getId() + " ] successfully.");
				return new ResponseEntity<Map<String, Object>>(
						new HttpResult(Constant.RESULT_STATUS_SUCCESS,
								"Custom [ " + savedCustom.getId() + " ] raised successfully.").build(),
						HttpStatus.OK);
			}
		}
		return null;
	}
	
	@RequestMapping(value = "/customs", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> findCustoms(@RequestParam("type") String type,
			@RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "sort", required = false) String sort) {
		if (null != type) {
			
			Page<Custom> customPage = this.customRepository.findByType(type, 
					new PageRequest(pageNumber, pageSize, MongoUtils.buildSort(sort)));
			List<Custom> content = customPage.getContent();
			return new ResponseEntity<Map<String, Object>>(
					new HttpResult(Constant.RESULT_STATUS_SUCCESS,
							"Custom found successfully.", content).build(),
					HttpStatus.OK);
		}
		return null;
	}
	
	@RequestMapping(value = "/custom", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Map<String, Object>> findCustom(@RequestParam("customId") String id) {
		if (null != id) {
			Custom foundCustom = this.customRepository.findOne(id);
			if (null != foundCustom) {
				return new ResponseEntity<Map<String, Object>>(
						new HttpResult(Constant.RESULT_STATUS_SUCCESS,
								"Found custom [ " + foundCustom.getId() + " ].", foundCustom).build(),
						HttpStatus.OK);
			}
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE,
						"Found custom failed.").build(),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/custom/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<Map<String, Object>> deleteCustom(@PathVariable("id") String id) {
		if (null != id) {
			Custom foundCustom = this.customRepository.findOne(id);
			if (null != foundCustom) {
				foundCustom.setIsDeleted(true);
				foundCustom.setDeletedDate(new Date().getTime());
				Custom updatedCustom = this.customRepository.save(foundCustom);
				if (null != updatedCustom) {
					logger.info("Set custom " + updatedCustom.getId() + " deleted as [true]  successfully.");
					return new ResponseEntity<Map<String, Object>>(
							new HttpResult(Constant.RESULT_STATUS_SUCCESS,
									"Set custom " + updatedCustom.getId() + " deleted as [true]  successfully.").build(),
							HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE,
						"Set ticket deleted as [true]  failed.").build(),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Map<String, Object>> signUp(
			@RequestBody CustomSignUpMessage msg) {
		Custom custom = this.customRepository.findOne(msg.getCustomId());
		if (null != custom) {
			msg.setSignUpDate(new Date().getTime());
			CustomSignUpMessage savedMsg = this.customSignUpMessageRepository.save(msg);
			if (null != savedMsg) {
				Set<CustomSignUpMessage> signUpSet = custom.getSignUpSet();
				if (null == signUpSet)
					signUpSet = new HashSet<CustomSignUpMessage> ();
				signUpSet.add(savedMsg);
				custom.setSignUpSet(signUpSet);
				this.customRepository.save(custom);
			}
			User foundUser = this.userRepository.findByOpenid(msg.getOpenid());
			if (null != foundUser) {
				Set<String> set = foundUser.getSignUpCustomResourceSet();
				if (null == set)
					set = new HashSet<String>();
				set.add(savedMsg.getCustomId());
				foundUser.setSignUpCustomResourceSet(set);
				foundUser.setWechatId(savedMsg.getWechatId());
				this.userRepository.save(foundUser);
			}
				return new ResponseEntity<Map<String, Object>>(
						new HttpResult(Constant.RESULT_STATUS_SUCCESS,
								"Set custom signUpSet successfully.", true).build(),
						HttpStatus.OK);
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE,
						"Set custom signUpSet failed.", false).build(),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/signUp/msg", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Map<String, Object>> findSignUpMsgs(
			@RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "sort", required = false) String sort) {
		Page<CustomSignUpMessage> msgPage = this.customSignUpMessageRepository
				.findAll(new PageRequest(pageNumber, pageSize, MongoUtils.buildSort(sort)));
		List<CustomSignUpMessage> msgs = msgPage.getContent();
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_SUCCESS,
						"Find custom signup msgs successfully.", msgs).build(),
				HttpStatus.OK);
	}

}
