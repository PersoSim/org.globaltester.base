package org.globaltester.core.ui.views;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.navigator.CommonNavigator;

public class GlobalTesterNavigator extends CommonNavigator {

	@Override
	protected Object getInitialInput() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}

}
