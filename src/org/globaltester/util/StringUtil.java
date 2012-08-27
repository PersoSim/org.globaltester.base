package org.globaltester.util;

public class StringUtil {

	/**
	 * Replaces leading whitespaces (tabs to spaces) for all lines and unindents
	 * all lines to a common level. Additionally all empty lines are removed.
	 * 
	 * @param code
	 * @return
	 */
	public static String formatCode(String code) {
		String[] lines = code.split("\n");
		// replace all leading tabs with whitespaces
		for (int i = 0; i < lines.length; i++) {
			String remainingCurrentLine = lines[i];
			String whitespace = "";
			while (remainingCurrentLine.length() > 0) {
				if (remainingCurrentLine.startsWith(" ")) {
					whitespace += " ";
					remainingCurrentLine = remainingCurrentLine.substring(1);
				} else if (remainingCurrentLine.startsWith("\t")) {
					whitespace += "    ";
					remainingCurrentLine = remainingCurrentLine.substring(1);
				} else {
					break;
				}
			}
			if (remainingCurrentLine.length() > 0) {
				lines[i] = whitespace + remainingCurrentLine;
			} else {
				lines[i] = "";
			}
		}

		// prepare a common whitespace string
		String whiteLine = " ";
		for (int i = 0; i < lines.length; i++) {
			while (whiteLine.length() < lines[i].length()) {
				whiteLine += " ";
			}
		}

		// Unindent as much as there are common whitespaces at all lines
		int unindentChars = getCommonPrefix(whiteLine, lines).length();
		String res = "";
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].length() >= unindentChars) {
				res += lines[i].substring(unindentChars) + "\n";
			} else {
				res += "\n";
			}
		}

		// remove leading empty lines
		while (res.startsWith("\n")) {
			res = res.substring(1);
		}
		// remove trailing empty lines
		while (res.endsWith("\n")) {
			res = res.substring(0, res.length() - 1);
		}

		return res;
	}
	
	/**
	 * Find the longest common prefix for all non-empty strings in the given array
	 * starting the search with the given string.
	 * @param s
	 * @param arr
	 * @return
	 */
	public static String getCommonPrefix(String s, String[] arr) {
		if (s == null)
			return "";
		String commonPrefix = s;
		for (int i = 0; (i < arr.length) && commonPrefix.length() > 0; i++) {
			String curString = arr[i];
			if (curString.length() == 0)
				continue; // ignore empty strings
			while (!curString.startsWith(commonPrefix)) {
				commonPrefix = commonPrefix.substring(0,
						commonPrefix.length() - 1);
			}
			if (commonPrefix.length() <= 0)
				break; // the other
		}
		return commonPrefix;
	}

	static final String HEX_CHARS = "0123456789ABCDEF";



	public static String getHex(byte b) {
		StringBuilder hex = new StringBuilder(2);
		hex.append(HEX_CHARS.charAt((b & 0xF0) >> 4));
		hex.append(HEX_CHARS.charAt((b & 0x0F)));
		return hex.toString();
		
	}
	
	public static String getHex(byte[] bytes, int offset, int length) {
		byte[] raw = new byte[length];
		System.arraycopy(bytes, offset, raw, 0, length);
		
		StringBuilder hex = new StringBuilder(2 * raw.length);
		for (byte b : raw) {
			hex.append(getHex(b));
			hex.append(" ");
		}
		return hex.toString().trim();
	}

	public static byte[] parseHexString(String s) {
		s = s.replaceAll(" ", "");
		byte[] b = new byte[s.length() / 2];
		for (int i = 0; i < b.length; i++) {
			int index = i * 2;
			int v = Integer.parseInt(s.substring(index, index + 2), 16);
			b[i] = (byte) v;
		}
		return b;
	}
}
