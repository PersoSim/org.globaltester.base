package org.globaltester.util;


public class TLVUtil {
	
	/**
	 * Get the tag structure out of TLV structure as byte array beginning at offset.
	 *        
	 */
	public static byte[] getTag(byte[] ba, int offset) {
		
		//FIXME more other conditions are possible - Tag lengths are not bounded
		if ((ba[offset] & 0x1F) == 0x1F) {
			return new byte[] {ba[offset], ba[offset+1]};
		} else {
			return new byte[] {ba[offset]};
		}
	
	}
	
	/**
	 * Get the length of value bytes out of TLV structure beginning at offset position.
	 *           
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
					//FIXME Not allowed values in calculate the length out of HexByteArray must fix
					
			}
		} else {
			//FIXME Not allowed values in calculate the length out of HexByteArray must fix
			
		}
		return length;
	}
	
	/**
	 * Get the offset position of value bytes out of TLV structure.
	 *          
	 */
	public static byte[] getValue(byte[] ba, int offset) {
		//ToDo
		return new byte[] {ba[offset], ba[offset+1]};
	}
	
	/**
	 * Check recursive if byte array is a cascaded TLV.
	 * 
	 */
	public static boolean isValidTLV(byte[] ba, int offset) {
		boolean isValid = false;
		//ToDo
		
		return isValid;
	}
	
	/**
	 * Get offset in the TLV structure were the value begins.
	 * 
	 */
	public static int getValueOffset(byte[] ba, int offset) {
		
		//ToDo 		
		return offset;
	}
	
	/**
	 * Get offset in the TLV structure were the length encoding of the value bytes begins.
	 * 
	 */
	public static int getLengthOffset(byte[] ba, int offset) {
		//ToDo 		
		return offset;
	}
	
	
	/**
	 * Check if given byte array is a constructed TLV object.
	 * 
	 */
	public static boolean isConstructedTLV(byte[] ba, int offset) {
		boolean isConstructedTLV = false;

		byte[] checkConstructedTag = getTag(ba, offset);
		
		isConstructedTLV = ((checkConstructedTag[0] & 0x20) == 0x20);

		//FIXME The Tag byte is on offset position "0" were the constructed bit specifies the value is constructed, which means it again holds TLV values like a SET!
		
		return isConstructedTLV;
	}



	

}

