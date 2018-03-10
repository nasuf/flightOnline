package com.flight.utils;

public interface Constant {
	
	// Controller Result
	public static final String RESULT_STATUS = "status";
	public static final String RESULT_STATUS_SUCCESS = "success";
	public static final String RESULT_STATUS_FAILURE = "failure";
	public static final String RESULT_MESSAGE = "message";
	public static final String RESULT_DATA = "data";
	public static final String RESULT_ID_KEY = "id";
	
	public static final String OPEN_ID = "openid";
	public static final String ROLE = "role";
	public static final String IS_PAYED = "isPayed";

	
	// Wechat
	public static final String APPID = "wxc8a070e6d35c9e29";// test account: "  wxfa215961decf944c";
	public static final String SECRET = "93a36931405bd241f123f9497dca2792";// test account:  "  21028fef3a271a280c2712240bb0e3ba";
	
	public static final String AUTH_URL = "https://api.weixin.qq.com/sns/jscode2session?"
			+ "appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
	
	// User Role
	public static final String USER_ROLE_ADMIN = "ADMIN";
	public static final String USER_ROLE_USER = "USER";
	public static final String USER_ROLE_VIP = "VIP";
	
	// TOPIC | REPLY
	public static final String MESSAGE_TYPE = "TYPE";
	
	public static final String MESSAGE_TYPE_TOPIC = "TOPIC";
	public static final String MESSAGE_TYPE_REPLY = "REPLY";
	
	// TICKET | QUESTION | TOURISM | CUSTOM
	public static final String MESSAGE_SUBJECT = "SUBJECT";
	
	public static final String MESSAGE_SUBJECT_TICKET = "TICKET";
	public static final String MESSAGE_SUBJECT_QUESTION = "QUESTION";
	public static final String MESSAGE_SUBJECT_TOURISM = "TOURISM";
	public static final String MESSAGE_SUBJECT_CUSTOM = "CUSTOM";
	
	public static final String MESSAGE_ID = "MESSAGE_ID";
	public static final String MESSAGE_CONTENT = "MESSAGE_CONTENT";
	
	public static final Integer QUESTION_FIXED = 1;
	public static final Integer QUESTION_UNFIXED = 0;
	
}
