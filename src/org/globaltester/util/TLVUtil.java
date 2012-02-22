package org.globaltester.util;


public class TLVUtil {
	
	/**
	 * Return tag as byte array of length 2 
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
	 *            offset in ba to get the length bytes
	 */
	public static int getLength(byte[] ba, int offset) {
		int k, length = 0;
		
		if (ba.length >= 3) {
			
			// Three byte length
			if (ba[offset] == (byte) 0x82) {
				length = (int) ba[3];
				if (length < 0)
					length += 256;
				k = (int) ba[2];
				if (k < 0)
					k += 256;
				length += (k << 8);
				length += 4;
				
			// Two byte length
			} else if (ba[offset] == (byte) 0x81) {
				length = (int) ba[2];
				if (length < 0)
					length += 256;
					length += 3;
				
			// One byte length
			} else {
				length = (int) ba[offset];
				length += 2;
					assert length < 0 : "Invalid length in TLV structure";	
			}
		} else {
			assert ba.length < 3 : "Invalid TLV structure";
		}
		return length;
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

