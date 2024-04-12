package org.globaltester.base.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.List;

import org.globaltester.logging.BasicLogger;

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
	 * Method for reading and checking input streams for differences at byte
	 * level.
	 *
	 * @param expected
	 *            the {@link InputStream} which contains the expected content
	 * @param fileToCheck
	 *            the {@link InputStream} which should be checked for
	 *            differences
	 * @param contextSize
	 *            the number of bytes to store as context before differences
	 * @param expectedContext
	 *            context buffer for the expected file
	 * @param fileToCheckContext
	 *            context buffer for the file to be checked
	 * @return the position of the first differing byte or -1 if no differences
	 *         found
	 * @throws IOException
	 */
	public static long checkFilesForDifferences(InputStream expected, InputStream fileToCheck, int contextSize,
			List<Byte> expectedContext, List<Byte> fileToCheckContext) throws IOException {
		try (InputStreamReader currentlyMarshalledFileInputStreamReader = new InputStreamReader(fileToCheck);
				InputStreamReader previousFileInputStreamReader = new InputStreamReader(expected)) {

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
	 *
	 * @param list
	 * @return
	 */
	public static String toString(List<Byte> list) {
		StringBuilder builder = new StringBuilder();
		for (Byte b : list) {
			builder.append((char) (byte) b);
		}
		return builder.toString();
	}

	/**
	 * This method makes a quick check if the given Socket is already in use or
	 * not.
	 *
	 * @param host
	 *            as String
	 * @param port
	 *            number as int
	 * @return true if it already exists or false
	 */
	public static boolean isSocketAvailable(String host, int port) {
		InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);
		if (inetSocketAddress.getAddress() != null && (inetSocketAddress.getAddress().isAnyLocalAddress()
				|| inetSocketAddress.getAddress().isLoopbackAddress())) {
			try (ServerSocket socketTester = new ServerSocket()) {
				socketTester.setSoTimeout(180);
				socketTester.bind(inetSocketAddress);
				return true;
			} catch (IOException e) {
				BasicLogger.logException(Utils.class, e);
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * This returns the port number of a probably free port. Be aware of
	 * possible race conditions and prepare for exception handling if using this
	 * port number. This method can be used to get ephemeral port numbers that
	 * will work with high probability when used for opening sockets without the
	 * direct possibility to use ephemeral ports.
	 * @see ServerSocket#ServerSocket(int)
	 *
	 * @return the probably free port
	 * @throws IOException
	 */
	public static int getAvailablePort() throws IOException {
		ServerSocket socket = new ServerSocket(0);
		int port = socket.getLocalPort();
		socket.close();
		return port;
	}

	/**
	 *
	 * @param toLog
	 */
	public static void logErrToConsole(String toLog) {
		System.err.println(toLog);
	}
}
