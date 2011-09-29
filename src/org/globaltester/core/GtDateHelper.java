package org.globaltester.core;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GtDateHelper {
	static SimpleDateFormat defaultSdf = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public static String getCurrentTimeString(){
		return defaultSdf.format(Calendar.getInstance().getTime());
	}
}
