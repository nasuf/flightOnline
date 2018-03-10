package com.flight.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flight.model.User;
import com.flight.repository.UserRepository;
import com.flight.utils.Constant;
import com.flight.utils.HttpClientUtils;
import com.flight.utils.HttpResult;
import com.flight.utils.RedisUtil;

import redis.clients.jedis.Jedis;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private UserRepository userRepository;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@RequestMapping(value = "/openid", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> auth(@RequestParam("code") String code, HttpServletResponse response) {
		String AUTH_URL = Constant.AUTH_URL.replace("APPID", Constant.APPID)
				.replace("SECRET", Constant.SECRET)
				.replace("JSCODE", code);
		
		try {
			HttpResult result = HttpClientUtils.httpGetRequest(AUTH_URL);
			if (result.build().get(Constant.RESULT_STATUS).toString().equals("200")) {
				JsonNode node = MAPPER.readTree(result.build().get(Constant.RESULT_DATA).toString());
				String openid = node.get("openid").asText();
				User foundUser = this.userRepository.findByOpenid(openid);
				if (null == foundUser) {
					User user = new User();
					user.setOpenid(openid);
					user.setRole(Constant.USER_ROLE_USER);
					user.setAdminPwd(null);
					User savedUser = userRepository.save(user);
					if (null != savedUser) {
						logger.info("New user [ OPENID: " + savedUser.getOpenid() + " ] saved successfully.");
					}
					Map<String, Object> data = new HashMap<String, Object>();
					data.put(Constant.OPEN_ID, savedUser.getOpenid());
					data.put(Constant.ROLE, savedUser.getRole());
					return new ResponseEntity<Map<String, Object>>(
							new HttpResult(Constant.RESULT_STATUS_SUCCESS, "got session_key and openid", data).build(),
							HttpStatus.OK);
				} else {
					logger.info("User [ " + foundUser.getId() + " ] has been recorded.");
					Map<String, Object> data = new HashMap<String, Object>();
					data.put(Constant.OPEN_ID, foundUser.getOpenid());
					data.put(Constant.ROLE, foundUser.getRole());
					return new ResponseEntity<Map<String, Object>>(
							new HttpResult(Constant.RESULT_STATUS_SUCCESS, "found recorded user", data).build(),
							HttpStatus.OK);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> saveInfo(@RequestBody User userInfo, @RequestParam("openid") String openid) {
		if (null != userInfo && !StringUtils.isEmpty(openid)) {
			User foundUser = this.userRepository.findByOpenid(openid);
			if (null != foundUser) {
				foundUser.setNickName(userInfo.getNickName());
				foundUser.setAvatarUrl(userInfo.getAvatarUrl());
				foundUser.setCity(userInfo.getCity());
				foundUser.setGender(userInfo.getGender());
				foundUser.setLanguage(userInfo.getLanguage());
				foundUser.setProvince(userInfo.getProvince());
				
				User savedUser = this.userRepository.save(foundUser);
				if (null != savedUser) {
					logger.info("Update user [ OPENID: " + savedUser.getOpenid() + ", NICKNAME: " + savedUser.getNickName() + " successfully.");
					return new ResponseEntity<Map<String, Object>>(
							new HttpResult(Constant.RESULT_STATUS_SUCCESS, "update user: [ " + savedUser + " ] successfully.").build(),
							HttpStatus.OK);
				}
			} else {
				userInfo.setRole(Constant.USER_ROLE_USER);
				userInfo.setOpenid(openid);
				User savedUser = this.userRepository.save(userInfo);
				if (null != savedUser) {
					return new ResponseEntity<Map<String, Object>>(
							new HttpResult(Constant.RESULT_STATUS_SUCCESS, "New user: [ " + savedUser + " ] saved successfully.").build(),
							HttpStatus.OK);
				}
			}
		}
		return null;
	}
	
	@RequestMapping(value = "/validate/adminOnlineStatus", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> validateAdminLoginStatus(@RequestParam("adminLoginKey") String adminLoginKey,
			@RequestParam("openid") String openid) {
		Map<String, Object> data = new HashMap<String, Object>();
		RedisUtil redisUtil = new RedisUtil();
		Jedis jedis = RedisUtil.getJedis();
		// user ever login as admin, then check the login status
		String adminLoginValue = jedis.get(adminLoginKey);
		if (null != adminLoginValue) {
			// user is admin online now
			data.put("adminLoginStatus", true);
			return new ResponseEntity<Map<String, Object>>(
					new HttpResult(Constant.RESULT_STATUS_SUCCESS, "User [ " + openid + " ] is admin online now.", data).build(),
					HttpStatus.OK);
		} else {
			// user is admin offline now
			data.put("adminLoginStatus", false);
			return new ResponseEntity<Map<String, Object>>(
					new HttpResult(Constant.RESULT_STATUS_SUCCESS, "User [ " + openid + " ] is admin offline now.", data).build(),
					HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/getLatestUserRole", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getLatestUserRole(@RequestParam("openid") String openid) {
		if (null != openid) {
			User foundUser = this.userRepository.findByOpenid(openid);
			if (null != foundUser) {
				String role = foundUser.getRole();
				return new ResponseEntity<Map<String, Object>>(
						new HttpResult(Constant.RESULT_STATUS_SUCCESS, "Get latest user role successfully.", role).build(),
						HttpStatus.OK);
			}
		}
		return new ResponseEntity<Map<String, Object>>(
				new HttpResult(Constant.RESULT_STATUS_FAILURE, "Get latest user role failed.").build(),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/admin/login", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> admin(@RequestParam("openid") String openid, 
			@RequestBody String password, HttpSession session) {
		logger.info("openid:" + openid + ", password:" + password);
		User foundUser = this.userRepository.findByOpenid(openid);
		Map<String, Object> data = new HashMap<String, Object>();
		
		if (null != foundUser && foundUser.getRole().equals(Constant.USER_ROLE_ADMIN) 
				&& StringUtils.isNotEmpty(password)
				&& password.equals(foundUser.getAdminPwd())) {
			RedisUtil redisUtil = new RedisUtil();
			Jedis jedis = RedisUtil.getJedis();
			String adminLoginKey = UUID.randomUUID().toString();
			jedis.setex(adminLoginKey, 60*60, openid);
			
			data.put("adminLoginKey", adminLoginKey);
			data.put("adminLoginStatus", true);
			return new ResponseEntity<Map<String, Object>>(
					new HttpResult(Constant.RESULT_STATUS_SUCCESS, "User [ " + openid + " ] login as admin. [SUCCESS]", data).build(),
					HttpStatus.OK);
		} else {
			// password not valid
			data.put("adminLoginStatus", false);
			return new ResponseEntity<Map<String, Object>>(
					new HttpResult(Constant.RESULT_STATUS_SUCCESS, "User [ " + openid + " ] login as admin. [FAILED]", data).build(),
					HttpStatus.OK);
		}
	}

}
