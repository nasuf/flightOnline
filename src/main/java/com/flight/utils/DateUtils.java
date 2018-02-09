package com.flight.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	
	
	public static String formatWithTimeZone(Date date) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
		return sdf.format(date);
	}
	
	public static Integer getWeekOfYear(Date date) {
		  
		Calendar calendar = Calendar.getInstance();  
		calendar.setFirstDayOfWeek(Calendar.MONDAY);  
		calendar.setTime(date);  
		  
		System.out.println(calendar.get(Calendar.WEEK_OF_YEAR)); 
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}
	/*
	public static void main(String[] args) {
		System.out.println(DateUtils.getWeekOfYear(new Date(1514990628000L)));
	}*/

}
