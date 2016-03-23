package org.globaltester.base.ui.perspective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.globaltester.base.ui.views.GlobalTesterNavigator;

public class GlobalTesterPerspective implements IPerspectiveFactory {
	
	
	private static final String ID_FOLDER_TOPLEFT = "org.globaltester.base.ui.perspective.GlobalTesterPerspective.topLeft";
	private static final String ID_FOLDER_BOTTOMLEFT = "org.globaltester.base.ui.perspective.GlobalTesterPerspective.bottomLeft";
	private static final String ID_FOLDER_BOTTOM = "org.globaltester.base.ui.perspective.GlobalTesterPerspective.bottom";

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		
		 // Top left: GlobalTesterNavigator view
		 IFolderLayout topLeft = layout.createFolder(ID_FOLDER_TOPLEFT, IPageLayout.LEFT, 0.25f,
		    editorArea);
		 topLeft.addView(GlobalTesterNavigator.VIEW_ID);
		 
		 // Bottom left: Outline view and Property Sheet view
		 IFolderLayout bottomLeft = layout.createFolder(ID_FOLDER_BOTTOMLEFT, IPageLayout.BOTTOM, 0.50f,
		 	   ID_FOLDER_TOPLEFT);
		 bottomLeft.addView(IPageLayout.ID_OUTLINE);
		 bottomLeft.addView(IPageLayout.ID_PROP_SHEET);
		 
		 // Bottom right
		 layout.createFolder(ID_FOLDER_BOTTOM, IPageLayout.BOTTOM, 0.66f, editorArea);
		 
	}
	
	
}
