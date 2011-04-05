package org.globaltester.core;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;

@RunWith(AllTestsSuite.class) 
public class AllTests { 
   public static Class<?>[] suite() { 
	   List<Class<?>> list = new ArrayList<Class<?>>();
	   
	   //Add all test classes here
	   list.add(ActivatorTest.class);
	   
	   return (Class<?>[]) list.toArray(new Class<?>[0]);
   } 
}