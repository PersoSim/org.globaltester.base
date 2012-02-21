package org.globaltester.util;

public class ByteUtil {
	
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
		
		//return bs;
	}

}

