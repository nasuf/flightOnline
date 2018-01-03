package com.flight.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
	
	public String formatWithTimeZone(Date date) {
		return sdf.format(date);
	}
	
	public Integer getWeekOfYear(Date date) {
		  
		Calendar calendar = Calendar.getInstance();  
		calendar.setFirstDayOfWeek(Calendar.MONDAY);  
		calendar.setTime(date);  
		  
		System.out.println(calendar.get(Calendar.WEEK_OF_YEAR)); 
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}
	
	public static void main(String[] args) {
		/*String today = "2013-01-14";  
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
		Date date = null;  
		try {  
		    date = format.parse(today);  
		} catch (ParseException e) {  
		    // TODO Auto-generated catch block  
		    e.printStackTrace();  
		}  */
		
		Date date = new Date();
		  
		Calendar calendar = Calendar.getInstance();  
		calendar.setFirstDayOfWeek(Calendar.MONDAY);  
		calendar.setTime(date);  
		  
		System.out.println(calendar.get(Calendar.WEEK_OF_YEAR)); 
		
	}

}
