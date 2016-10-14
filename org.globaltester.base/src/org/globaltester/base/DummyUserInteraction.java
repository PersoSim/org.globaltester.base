package org.globaltester.base;

/**
 * Dummy implementation doing nothing. 
 * 
 * @author mboonk
 *
 */
public class DummyUserInteraction implements UserInteraction {

	@Override
	public void notifyUser(SeverityLevel level, String message) {
		//intentionally ignore
	}

	@Override
	public int askUser(String message, String... allowedResults) {
		return 0;
	}
}
