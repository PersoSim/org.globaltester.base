package org.globaltester.base.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ByteArrayUtil {
	
	public static int indexOf(byte [] data, byte b) {
		var index = -1;
		for (int i = 0; i < data.length; i++) {
			if (data[i]==b)
				return i;
		}
		return index;
	}
	
	/**
	 * This splits a given array at the delimiter while removing the delimiter from the result.
	 * 
	 * @param dataToSplit
	 * @param delimiter
	 * @return An array of byte arrays
	 */
	public static byte [][] splitAt(byte [] dataToSplit, byte delimiter){
		List<Integer> cuts = new ArrayList<>();
		cuts.add(-1);
		for (var i = 0; i < dataToSplit.length; i++) {
			if (dataToSplit[i] == delimiter)
				cuts.add(i);
		}
		cuts.add(dataToSplit.length);
		
		if (cuts.size() == 0)
			return new byte [][] { dataToSplit.clone() };
		
		var result = new byte [cuts.size() - 1][];
		for (var i = 0; i < cuts.size() - 1; i++) {
			result[i] = Arrays.copyOfRange(dataToSplit, cuts.get(i) + 1, cuts.get(i+1));
		}
		return result;
	}
}
