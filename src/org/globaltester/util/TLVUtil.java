package org.globaltester.util;

public class TLVUtil {
	
	
	public int checkLengthEncoding(byte[] bs) {
		
		System.out.println("Checking length encoding for byte buffer: " + bs.toString());

		if (bs.length == 0) {
			System.out.println("Empty byte string!");
		}

		
		byte firstByte = bs[0];
		
		int length = 0;
			
		if (firstByte == 0x81) {
			
			System.out.println("Two byte length");
			
			if (bs.length >= 2) {
				byte secondByte = bs[1];
				System.out.println("secondByte: " + secondByte);
				
				if (128 <= secondByte && secondByte < 255)
					System.out.println("secondByte not in range!");

				length = secondByte;
			} else {
				System.out.println("Wrong length encoding, expecting two bytes!");
			}
			
			
		} else if (firstByte == 0x82) {
		
			if (bs.length >= 2) {
				System.out.println("Three byte length");
					
				byte a1 = bs[1];
				byte a2 = bs[2];
				
				length = (a1 << 8) | a2;

			} else {
				System.out.println("Wrong length encoding, expecting three bytes!");
			}
			
		} else {
		
			System.out.println("One byte length");
			
			if (0 <= firstByte && firstByte <= 127)
				System.out.println("firstByte not in range!");
			
			length = firstByte;
		} 
		
		System.out.println("Encoded length: " + length);
		
		return length;
	}

}
