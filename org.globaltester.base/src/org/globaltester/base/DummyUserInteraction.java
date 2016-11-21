package org.globaltester.base;

import java.util.List;

/**
 * Dummy implementation doing nothing. 
 * 
 * @author mboonk
 *
 */
public class DummyUserInteraction implements UserInteraction {

	@Override
	public void notify(SeverityLevel level, String message) {
		//intentionally ignore
	}

	@Override
	public int select(String message, List<PropertyElement> properties, String... allowedResults) {
		return 0;
	}
}
