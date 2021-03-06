package org.globaltester.base.util;


public class TLVUtil {
	
	/**
	 * Get the tag structure out of TLV structure as byte array beginning at offset.
	 *
	 * @param ba
	 *            containing the TLV structure
	 * @param offset
	 *            offset in ba to get the tag
	 * @return byte array of length one or two if two byte tag found at offset position
	 */
	public static byte[] getTag(byte[] ba, int offset) {
		
		//IMPL more other conditions are possible - Tag lengths are not bounded
		if ((ba[offset] & 0x1F) == 0x1F) {
			return new byte[] {ba[offset], ba[offset+1]};
		} else {
			return new byte[] {ba[offset]};
		}
	
	}
	
	/**
	 * Get the length of value bytes out of TLV structure beginning at offset position.
	 *
	 * @param ba
	 *            containing the TLV structure
	 * @param offset
	 *            offset in ba were the length encoding begins
	 * @return
	 * 		  	  length
	 */
	public static int getLength(byte[] ba, int offset) {
		int k, length = 0;
		
		//ToDo 
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
					
				// Not allowed values in calculate the length out of HexByteArray must fix
					
			}
		} else {
			// Not allowed values in calculate the length out of HexByteArray must fix
			
		}
		return length;
	}
	
	/**
	 * Get the offset position of value bytes out of TLV structure.
	 * 
	 * @param ba
	 *            containing the TLV structure
	 * @param offset
	 *            offset in ba to get the value
	 * @return value
	 * 			  as byte array at offset position in TLV structure
	 */
	public static byte[] getValue(byte[] ba, int offset) {
		//ToDo
		return new byte[] {ba[offset], ba[offset+1]};
	}
	
	/**
	 * Check recursive if byte array is a cascaded TLV.
	 * 
	 * @param ba
	 *            containing the TLV structure
	 * @return
	 * 	      	  true or false depending of a correct TLV structure
	 */
	public static boolean isValidTLV(byte[] ba, int offset) {
		boolean isValid = false;
		//ToDo
		
		return isValid;
	}
	
	/**
	 * Get offset in the TLV structure were the value begins.
	 * 
	 * @param ba
	 *            containing the TLV structure
	 * @return
	 * 	      	  offset
	 */
	public static int getValueOffset(byte[] ba, int offset) {
		
		//ToDo 		
		return offset;
	}
	
	/**
	 * Get offset in the TLV structure were the length encoding of the value bytes begins.
	 * 
	 * @param ba
	 *            containing the TLV structure
	 * @return
	 * 	      	  offset
	 */
	public static int getLengthOffset(byte[] ba, int offset) {
		//ToDo 		
		return offset;
	}
	
	
	/**
	 * Check if given byte array is a constructed TLV object.
	 * 
	 * @param ba
	 *            containing the TLV structure
	 * @return
	 * 	      	  true or false depending of ba is a constructed TLV object
	 */
	public static boolean isConstructedTLV(byte[] ba, int offset) {
		boolean isConstructedTLV = false;

		byte[] checkConstructedTag = getTag(ba, offset);
		
		isConstructedTLV = ((checkConstructedTag[0] & 0x20) == 0x20);
		
		return isConstructedTLV;
	}



	

}

