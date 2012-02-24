package org.globaltester.util;


public class TLVUtil {
	
	/**
	 * Get the tag out of (T)LV structure as byte array of length two or one depending on tag found at offset
	 * 
	 * @param ba
	 *            byte array 
	 * @param offset
	 *            offset in ba to get the tag
	 * @return byte array of length one or two if two byte tag found at offset position            
	 */
	public static byte[] getTag(byte[] ba, int offset) {
		
		byte tagNumber = ba[offset];
		if ((tagNumber & 0x7F) == 0x7F) {
			return new byte[] {ba[offset], ba[offset+1]};
		} else {
			return new byte[] {ba[offset]};
		}
	
	}
	
	/**
	 * Get the length out of T(L)V structure
	 * 
	 * @param ba
	 *            byte array 
	 * @param offset
	 *            offset in ba were the length encoding begins
	 * @return
	 * 		  length            
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
	
	/**
	 * Get the value out of TL(V) structure
	 * 
	 * @param ba
	 *            byte array 
	 * @param offset
	 *            offset in ba to get the value
	 * @return value 
	 * 			  as byte array at offset position in TL(V) structure           
	 */

	public static byte[] getValue(byte[] ba, int offset) {
		//ToDo
		return new byte[] {ba[offset], ba[offset+1]};
	}
	
	/**
	 * Proof if byte array is a valid TLV structure
	 * 
	 * @param ba
	 *            byte array 
	 * @return 
	 * 	      	  true or false depending of a correct TLV structure
	 */
	public static boolean isValidTLV(byte[] ba) {
		boolean isValid = false;
		//ToDo
		
		return isValid;
	}
	
	/**
	 * Get offset in the TLV structure were the value begins
	 * 
	 * @param ba
	 *            byte array 
	 * @return 
	 * 	      	  offset 
	 */
	public static int getValueOffset(byte[] ba) {
		int offset = 0;
		//ToDo 		
		return offset;
	}
	
	/**
	 * Get offset in the TLV structure were the length encoding of the value begins
	 * 
	 * @param ba
	 *            byte array 
	 * @return 
	 * 	      	  offset
	 */
	public static int getLengthOffset(byte[] ba) {
		int offset = 0;
		//ToDo 		
		return offset;
	}
	
	/**
	 * Check if given byte array of length two is a correct TwoByteTag
	 * 
	 * @param ba
	 *            byte array 
	 * @return 
	 * 	      	  boolean
	 */
	public static boolean is2ByteTag(byte[] ba) {
		boolean isTwoByteTag = false;
		//ToDo 		
		return isTwoByteTag;
	}
	
	/**
	 * Check if given byte array is a constructed TLV object
	 * 
	 * @param ba
	 *            byte array 
	 * @return 
	 * 	      	  boolean
	 */
	public static boolean isConstructedTLV(byte[] ba) {
		boolean isConstructedTLV = false;

		byte[] checkConstructedTag = getTag(ba, 0);
		
		if (checkConstructedTag.length == 1) 	
			isConstructedTLV = ((checkConstructedTag[0] & 0x20) == 0x20);

		assert checkConstructedTag.length > 1 : "Check constructed TLV tag must have length 1!";
		
		return isConstructedTLV;
	}



	

}

