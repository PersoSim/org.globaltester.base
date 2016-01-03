package org.globaltester.core;

import org.globaltester.core.document.export.ExporterTest;
import org.globaltester.core.document.export.ZipHandlerTest;
import org.globaltester.core.util.ByteUtilTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ActivatorTest.class, ByteUtilTest.class, ExporterTest.class, GtDateHelperTest.class, ZipHandlerTest.class})
public class SmokeTestSuite {

}
