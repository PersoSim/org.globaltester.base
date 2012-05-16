package org.globaltester.core;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GtDateHelper {
	static final String gtDefaultDateFormat = "yyyyMMdd_Hmmss"; 
	
	public static String getCurrentTimeString(){
		SimpleDateFormat defaultSdf = new SimpleDateFormat(gtDefaultDateFormat);
		return defaultSdf.format(Calendar.getInstance().getTime());
	}
}
