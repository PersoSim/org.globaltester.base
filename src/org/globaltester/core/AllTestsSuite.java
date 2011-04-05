package org.globaltester.core;

import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

public class AllTestsSuite extends Suite { 
    public AllTestsSuite(Class<?> setupClass) throws InitializationError { 
       super(setupClass, (Class<?>[]) AllTests.suite()); 
    } 
} 