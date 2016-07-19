package org.globaltester.base;

public class DummyUserInteraction implements UserInteraction {

	@Override
	public void notifyUser(SeverityLevel level, String message) {
		
	}

	@Override
	public UserQuestionResult askUser(String message) {
		return UserQuestionResult.OK;
	}

}
