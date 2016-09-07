package org.globaltester.base;

/**
 * This encodes the result of a request to the user.
 * 
 * @author mboonk
 *
 */
public enum UserQuestionResult {
	OK, CANCEL, RESUME, SKIP;

	/**
	 * @return true, iff the result encodes a positive result in a generic sense
	 */
	public boolean isPositive() {
		return this.equals(OK) || this.equals(RESUME);
	}
}
