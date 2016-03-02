package org.globaltester.base.util;

public class ByteUtil {
	
	/**
	 * Modify byte array with increment byte at index position - if byte in array is MaxValue 0xFF or a char "<" set byte value to 0x31 = "1" 
	 * 
	 * @param ba
	 * 			Byte array
	 * @param index
	 */
	
	/*
	 * Increment byte at index position in ba
	 */
	public static byte[] incrementByteAtIndex(byte[] ba, int index) {
		
		if (index >= 0 && index <= ba.length) {
			char c = (char) ba[index]; 
			
			// check if lastByte = '<'
			if (c == '<' || ba[index] == 0xFF) {
				// set to 1 if the char on this position is a checksum filler in the mrz string or byte MaxValue
				ba[index] = 0x31;
			} else {
				ba[index]++;
			}
		} else {
			//FIXME ERRORHANDLING
		}
		return ba;
	}

}

