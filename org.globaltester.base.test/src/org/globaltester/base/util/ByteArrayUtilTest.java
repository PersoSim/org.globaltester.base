package org.globaltester.base.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ByteArrayUtilTest {
	@Test
	public void testIndexOf(){
		byte [] input = {0,1,100,2,3,4,100,5,6,7,8,9};
		assertEquals(2, ByteArrayUtil.indexOf(input, (byte)100));
	}
	@Test
	public void testIndexOfNoMatch(){
		byte [] input = {0,1,2,3,4,5};
		assertEquals(-1, ByteArrayUtil.indexOf(input, (byte)100));
	}
	@Test
	public void testSplitNoMatch(){
		byte [] input = {0,1,2,3,4,5};
		byte [][] splitresult = ByteArrayUtil.splitAt(input, (byte)100);
		assertEquals(1, splitresult.length);
		assertArrayEquals(input, splitresult[0]);
	}
	@Test
	public void testSplit(){
		byte [] input = {0,1,2,100,100,3,4,5,100};
		byte [][] splitresult = ByteArrayUtil.splitAt(input, (byte)100);
		assertEquals(4, splitresult.length);
		assertArrayEquals(splitresult[0], new byte [] {0,1,2});
		assertArrayEquals(splitresult[1], new byte [0]);
		assertArrayEquals(splitresult[2], new byte [] {3,4,5});
		assertArrayEquals(splitresult[3], new byte [0]);
	}
}
