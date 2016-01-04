package org.globaltester.core.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.globaltester.util.StringUtil;
import org.junit.Test;

public class StringUtilTest {
	@Test
	public void testParseHexString(){
		String testString = "00 FF";
		byte [] expectedResult = new byte [] {0x00, (byte) 0xFF};
		byte [] result = StringUtil.parseHexString(testString);
		assertArrayEquals(expectedResult, result);
	}
	
	@Test
	public void testParseHexStringEmptyString(){
		String testString = "";
		byte [] expectedResult = new byte [0];
		byte [] result = StringUtil.parseHexString(testString);
		assertArrayEquals(expectedResult, result);
	}
	
	@Test
	public void testGetHex(){
		assertEquals("FF", StringUtil.getHex((byte) 0xFF));
		assertEquals("00", StringUtil.getHex((byte) 0x00));
		
		byte[] input = new byte [] {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		String expectedResult = "02 03 04 05 06 07 08 09 0A 0B 0C 0D";
		assertEquals(expectedResult, StringUtil.getHex(input, 2, 12));
	}
	
	@Test
	public void testFormatCode(){
		String input = "\tmethod();\n method();\n  method();\n\n";
		String expectedResult = "   method();\nmethod();\n method();";
		String actualResult = StringUtil.formatCode(input);
		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	public void testGetCommonPrefix(){
		String [] input = new String []{"commonprefixteststring", "commonprefix", "commonprefixtest", "common"};
		String prefix = "commonprefix";
		String expectedResult = "common";
		assertEquals(expectedResult, StringUtil.getCommonPrefix(prefix, input));
	}
}
