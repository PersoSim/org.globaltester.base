package org.globaltester.base;

/**
 * This encodes the result of a request to the user.
 * 
 * @author mboonk
 *
 */
public enum UserQuestionResult {
	OK, CANCEL;

	/**
	 * @return true, iff the result encodes a positive result in a generic sense
	 */
	public boolean isPositive() {
		if (this.equals(OK)) {
			return true;
		}
		return false;
	}
}
