package org.globaltester.base.interfaces;

public interface ITreeObservable {

	public void removeTreeChangeListener(ITreeChangeListener oldListener);

	public void addTreeChangeListener(ITreeChangeListener newListener);

}
