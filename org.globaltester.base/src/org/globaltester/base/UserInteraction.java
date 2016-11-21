package org.globaltester.base;

import java.util.List;

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
	public void notify(SeverityLevel level, String message);

	/**
	 * This requests an input from the user.
	 * 
	 * @param message
	 *            the question to as
	 * @param properties
	 *            a list of properties to be transmitted
	 * @return the index of the result of the interaction
	 */
	public int select(String message, List<PropertyElement> properties, String... allowedResults);
}
