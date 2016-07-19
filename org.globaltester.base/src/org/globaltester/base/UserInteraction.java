package org.globaltester.base;

public interface UserInteraction {
	public void notifyUser(SeverityLevel level, String message);
	
	public UserQuestionResult askUser(String message);
}
