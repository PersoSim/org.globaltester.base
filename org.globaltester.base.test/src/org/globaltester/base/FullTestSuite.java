package org.globaltester.base;

import org.globaltester.base.resources.GtResourceHelperTest;
import org.globaltester.base.util.ByteUtilTest;
import org.globaltester.base.util.StringUtilTest;
import org.globaltester.base.xml.XmlHelperTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ByteUtilTest.class, StringUtilTest.class, GtResourceHelperTest.class, StringUtilTest.class, XmlHelperTest.class})
public class FullTestSuite {

}
