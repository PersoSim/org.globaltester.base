package org.globaltester.base;

public enum UserQuestionResult {
	OK, CANCEL;
	
	public boolean isPositive(){
		if (this.equals(OK)){
			return true;
		}
		return false;
	}
}
