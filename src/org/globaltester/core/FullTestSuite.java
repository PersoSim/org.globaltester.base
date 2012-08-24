package org.globaltester.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ActivatorTest.class, ByteUtilTest.class, ExportTest.class, GtDateHelperTest.class, ZipHandlerTest.class})
public class FullTestSuite {

}
