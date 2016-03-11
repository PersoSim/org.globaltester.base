package org.globaltester.base.interfaces;

public interface ITreeChangeListener {
	
	/**
	 * Notify listeners of changed model elements
	 *
	 * @param notifier	object that notifies about the change
	 * @param structureChanged true if the tree structure has changed instead of properties only
	 * @param changedElements model elements that changed
	 * @param properties all changed properties
	 * 
	 */
	public void notifyTreeChange(Object notifier, boolean structureChanged,
			Object[] changedElements, String[] properties);

}
