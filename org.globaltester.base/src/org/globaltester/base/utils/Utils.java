package org.globaltester.base.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * This class contains generic utilities that can not usefully be bundled
 * elsewhere.
 * 
 * @author mboonk
 *
 */
public class Utils {
	/**
	 * This method reads the stack trace that the given {@link Exception} would
	 * print and returns it as a {@link String}.
	 * 
	 * @param exception
	 * @return the stack trace as string
	 */
	public static String getStacktrace(Exception exception) {
		ByteArrayOutputStream stacktraceBytes = new ByteArrayOutputStream();
		exception.printStackTrace(new PrintStream(stacktraceBytes));
		return stacktraceBytes.toString();
	}
}
