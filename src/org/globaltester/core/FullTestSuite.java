package org.globaltester.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ActivatorTest.class, ExportTest.class, ZipHandlerTest.class, GtDateHelperTest.class, ByteUtilTest.class})
public class FullTestSuite {

}
