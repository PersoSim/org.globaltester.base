package org.globaltester.base.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

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
	

	
	/**
	 * Method for checking files for differences at byte level.
	 * @param expected the {@link File} which contains the expected content
	 * @param fileToCheck the {@link File} which should be checked for differences
	 * @param contextSize the number of bytes to store as context before differences
	 * @param expectedContext context buffer for the expected file
	 * @param fileToCheckContext context buffer for the file to be checked
	 * @return the position of the first differing byte or -1 if no differences found
	 * @throws IOException
	 */
	public static long checkFilesForDifferences(File expected, File fileToCheck, int contextSize, LinkedList<Byte> expectedContext,
			LinkedList<Byte> fileToCheckContext) throws IOException {
		try (InputStreamReader currentlyMarshalledFileInputStreamReader = new InputStreamReader(
				new FileInputStream(fileToCheck));
				InputStreamReader previousFileInputStreamReader = new InputStreamReader(
						new FileInputStream(expected));) {

			int fileToCheckReadByte = 0;
			int expectedReadByte = 0;
			long positionInFile = 0;

			do {
				fileToCheckReadByte = currentlyMarshalledFileInputStreamReader.read();
				expectedReadByte = previousFileInputStreamReader.read();

				fileToCheckContext.add((byte) fileToCheckReadByte);
				expectedContext.add((byte) expectedReadByte);
				if (fileToCheckContext.size() >= contextSize) {
					fileToCheckContext.remove(0);
				}
				if (expectedContext.size() >= contextSize) {
					expectedContext.remove(0);
				}

				if (fileToCheckReadByte != expectedReadByte) {
					return positionInFile;
				}
				positionInFile++;
			} while (fileToCheckReadByte != -1);

		}

		return -1;
	}

	/**
	 * Converts a list of bytes to a string using UTF-8
	 * @param list
	 * @return
	 */
	public static String toString(List<Byte> list) {
		StringBuilder builder = new StringBuilder();
		for (Byte b : list){
			builder.append((char)(byte)b);
		}
		return builder.toString();
	}
}
