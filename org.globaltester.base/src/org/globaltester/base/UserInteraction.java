package org.globaltester.base;

/**
 * This provides functionality for communicating with the user.
 * 
 * @author mboonk
 *
 */
public interface UserInteraction {
	/**
	 * This presents a notification to the user.
	 * 
	 * @param level
	 *            the severity of the notification
	 * @param message
	 *            the message to show to the user
	 */
	public void notifyUser(SeverityLevel level, String message);

	/**
	 * This requests an input from the user.
	 * 
	 * @param message
	 *            the question to as
	 * @return the result of the interaction
	 */
	public UserQuestionResult askUser(String message);
}
