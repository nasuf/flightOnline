package com.flight.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Tourism {
	
	@Id
	@NotNull(message = "Id can't be null.")
	private String id;
	
	@Field
	private String title;
	
	@Field
	private String poster;
	
	@Field
	private String postDate;
	
	@Field
	private String festival;
	
	@Field
	private String departureDate;
	
	@Field
	private String content;
	
	@Field
	private Integer participants;
	
	@Field
	private Boolean deleted;
	
	@Field
	private List<String> replyList;
	
	@Field
	private Integer replyCnt;

}
