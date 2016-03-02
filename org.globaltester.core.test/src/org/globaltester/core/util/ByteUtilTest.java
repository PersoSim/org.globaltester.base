package org.globaltester.core.util;

import static org.junit.Assert.assertArrayEquals;

import org.globaltester.base.util.ByteUtil;
import org.junit.Test;

public class ByteUtilTest {
	@Test
	public void testIncrementByteAtIndex(){
		byte [] input = {0,0,0,0};
		byte [] expectedResult = {0,0,1,0};
		byte [] result = ByteUtil.incrementByteAtIndex(input, 2);
		assertArrayEquals(expectedResult, result);
	}
}
