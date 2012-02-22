package org.globaltester.util;

public class ByteUtil {
	
	/**
	 * Modify byte array with increment last byte - if last last byte in array is MaxValue 0xFF or a char "<" set byte value to 0x31 = "1" 
	 * 
	 * @param bs
	 * 			Byte array
	 */
	
	/*
	 * Increment the last byte of the byte array
	 */
	public void incrementLastByte(byte[] bs) {
		
		// get the index of the last byte in byte array
		int index = bs.length-1;
		
		char c = (char) bs[index]; 
		
		// check if lastByte = '<'
		if (c == '<' || bs[index] == 0xFF) {
			// set to 1 if the char on this position is a checksum filler in the mrz string or byte MaxValue
			bs[index] = 0x31;
		} else {
			bs[index]++;
		}
	}

}

