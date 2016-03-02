package org.globaltester.core;

import org.globaltester.core.renderer.GtFopHelperTest;
import org.globaltester.core.resources.GtResourceHelperTest;
import org.globaltester.core.util.ByteUtilTest;
import org.globaltester.core.util.StringUtilTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ActivatorTest.class, ByteUtilTest.class, StringUtilTest.class, GtResourceHelperTest.class, GtFopHelperTest.class})
public class FullTestSuite {

}
