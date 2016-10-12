package org.globaltester.base;

public class DummyUserInteraction implements UserInteraction {

	@Override
	public void notifyUser(SeverityLevel level, String message) {
		
	}

	@Override
	public int askUser(String message, String... allowedResults) {
		return 0;
	}
}
