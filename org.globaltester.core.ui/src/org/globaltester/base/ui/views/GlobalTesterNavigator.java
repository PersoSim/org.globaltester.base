package org.globaltester.base.ui.views;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.navigator.CommonNavigator;

public class GlobalTesterNavigator extends CommonNavigator {
	public static final String VIEW_ID = "org.globaltester.base.ui.views.GlobalTesterNavigator";

	@Override
	protected Object getInitialInput() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}

}
