package org.globaltester.util;

public class StringUtil {

	/**
	 * Replaces leading whitespaces (tabs to spaces) for all lines and unindents
	 * all lines to a common level
	 * 
	 * @param code
	 * @return
	 */
	public static String formatCode(String code) {
		String[] lines = code.split("\n");
		//replace all leading tabs with whitespaces
		for (int i = 0; i < lines.length; i++) {
			String remainingCurrentLine = lines[i];
			String whitesapce = "";
			while (remainingCurrentLine.length() > 0) {
				if (remainingCurrentLine.startsWith(" ")) {
					whitesapce += " ";
					remainingCurrentLine = remainingCurrentLine.substring(1);
				} else if (remainingCurrentLine.startsWith("\t")) {
					whitesapce += "    ";
					remainingCurrentLine = remainingCurrentLine.substring(1);
				} else {
					break;
				}
			}
			if (remainingCurrentLine.length() > 0) {
				lines[i] = whitesapce + remainingCurrentLine;
			} else {
				lines[i]  ="";
			}
		}
	
		//prepare a common whitespace string
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
		
		//remove leading empty lines
		while (res.startsWith("\n")) {
			res = res.substring(1);
		}
		//remove trailing empty lines
		while (res.endsWith("\n")) {
			res = res.substring(0, res.length()-1);
		}
		
		return res;
	}

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
				System.out.println(commonPrefix + "*");
			}
			if (commonPrefix.length() <= 0)
				break; // the other
		}
		return commonPrefix;
	}
}
