package org.globaltester.util;

public class TLVUtil {
	
	/**
	 * Return Tag as byte array of length 2 
	 * 
	 * @param bs
	 *            byte array 
	 * @param offset
	 *            offset in bs to get the tag
	 */
	public static byte[] getTag(byte[] bs, int offset) {
		
		byte tagNumber = bs[offset];
		if ((tagNumber & 0x7F) == 0x7F) {
			return new byte[] {bs[offset], bs[offset+1]};
		} else {
			return new byte[] {bs[offset]};
		}
	
	}
	
	/**
	 * Return length of TLV structure
	 * 
	 * @param bs
	 *            byte array 
	 * @param offset
	 *            offset in bs to get the length bytes
	 */
	public static int getLength(byte[] bs, int offset) {
		//ToDo
		
		return 0;
	}
	
	public static byte[] getValue(byte[] bs, int offset) {
		
		return new byte[] {bs[offset], bs[offset+1]};
	}
	
	public static byte getNext(byte[] bs) {
		byte value = 0;
		//ToDo
		
		return value;
	}

}

