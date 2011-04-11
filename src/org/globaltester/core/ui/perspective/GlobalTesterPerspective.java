package org.globaltester.core.ui.perspective;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class GlobalTesterPerspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setFixed(true);
		
		layout.addStandaloneView("org.globaltester.core.ui.navigator",  true, IPageLayout.LEFT, 0.3f, editorArea);
	}
}
