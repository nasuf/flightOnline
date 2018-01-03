package com.flight.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
	
	public String formatWithTimeZone(Date date) {
		return sdf.format(date);
	}

}
