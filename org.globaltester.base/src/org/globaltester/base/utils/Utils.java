package org.globaltester.base.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Utils {
	public static String getStacktrace(Exception exception){
		ByteArrayOutputStream stacktraceBytes = new ByteArrayOutputStream();
		exception.printStackTrace(new PrintStream(stacktraceBytes));
		return stacktraceBytes.toString();
	}
}
