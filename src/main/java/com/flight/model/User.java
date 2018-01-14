package com.flight.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class User {

	@Id
	@NotNull(message = "Id can't be null.")
	private String id;
	
	@Field
	private String nickName;
	
	@Field
	private String openid;
	
	@Field
	private Integer gender;
	
	@Field
	private String avatarUrl;
	
	@Field
	private String language;
	
	@Field
	private String city;
	
	@Field
	private String province;
	
	@Field
	private String role;
	
	@Field
	private String adminPwd;
	
	@Field
	private List<Map<String, Object>> messageList;
	
	@Field
	private String wechatId;
	
	@Field
	private Set<String> signUpTourismList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAdminPwd() {
		return adminPwd;
	}

	public void setAdminPwd(String adminPwd) {
		this.adminPwd = adminPwd;
	}
	

	public List<Map<String, Object>> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<Map<String, Object>> messageList) {
		this.messageList = messageList;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public Set<String> getSignUpTourismList() {
		return signUpTourismList;
	}

	public void setSignUpTourismList(Set<String> signUpTourismList) {
		this.signUpTourismList = signUpTourismList;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", nickName=" + nickName + ", openid=" + openid + ", gender=" + gender
				+ ", avatarUrl=" + avatarUrl + ", language=" + language + ", city=" + city + ", province=" + province
				+ ", role=" + role + ", adminPwd=" + adminPwd + ", messageList=" + messageList
				+ ", wechatId=" + wechatId + ", signUpTourismList=" + signUpTourismList + "]";
	}

}
