package com.flight.utils;

public interface Constant {
	// Controller Param Key
	public static final String PARAM_USER_NAME_KEY = "userName";
	public static final String PARAM_PASSWORD_KEY = "password";
	public static final String PARAM_TYPE_KEY = "type";
	
	// Controller Result
	public static final String RESULT_STATUS = "status";
	public static final String RESULT_STATUS_SUCCESS = "success";
	public static final String RESULT_STATUS_FAILURE = "failure";
	public static final String RESULT_MESSAGE = "message";
	public static final String RESULT_DATA = "data";
	public static final String RESULT_ID_KEY = "id";
	
	public static final String OPEN_ID = "openid";

	
	// Wechat
	public static final String APPID = "wxfa215961decf944c";// test account: "wxa6060fbdcbb1611c";
	public static final String SECRET = "21028fef3a271a280c2712240bb0e3ba";// test account:  "d4624c36b6795d1d99dcf0547af5443d";
	
	public static final String AUTH_URL = "https://api.weixin.qq.com/sns/jscode2session?"
			+ "appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
	
	// User Role
	public static final String USER_ROLE_ADMIN = "admin";
	public static final String USER_ROLE_USER = "user";
	
}
