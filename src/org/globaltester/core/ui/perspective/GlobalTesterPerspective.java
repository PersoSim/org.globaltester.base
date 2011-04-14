package org.globaltester.core.ui.perspective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IPlaceholderFolderLayout;

public class GlobalTesterPerspective implements IPerspectiveFactory {

	private static final String ID_FOLDER_TOPLEFT = "org.globaltester.core.ui.perspective.GlobalTesterPerspective.topLeft";
	private static final String ID_FOLDER_BOTTOMLEFT = "org.globaltester.core.ui.perspective.GlobalTesterPerspective.bottomLeft";
	private static final String ID_FOLDER_BOTTOM = "org.globaltester.core.ui.perspective.GlobalTesterPerspective.bottom";
	private static final String ID_FOLDER_RIGHT = "org.globaltester.core.ui.perspective.GlobalTesterPerspective.right";

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		
		 // Top left: GlobalTesterNavigator view and Bookmarks view placeholder
		 IFolderLayout topLeft = layout.createFolder(ID_FOLDER_TOPLEFT, IPageLayout.LEFT, 0.25f,
		    editorArea);
		 topLeft.addView("org.globaltester.core.ui.views.GlobalTesterNavigator");
		 topLeft.addPlaceholder(IPageLayout.ID_BOOKMARKS);
		 

		 // Bottom left: Outline view and Property Sheet view
		 IFolderLayout bottomLeft = layout.createFolder(ID_FOLDER_BOTTOMLEFT, IPageLayout.BOTTOM, 0.50f,
		 	   ID_FOLDER_TOPLEFT);
		 bottomLeft.addView(IPageLayout.ID_OUTLINE);
		 
		 // Bottom right: Task List view
		 IFolderLayout bottom = layout.createFolder(ID_FOLDER_BOTTOM, IPageLayout.BOTTOM, 0.66f, editorArea);
		 bottom.addView(IPageLayout.ID_PROP_SHEET);
		 bottom.addView(IPageLayout.ID_PROGRESS_VIEW);
		 
		// Right: SCSH, TestLayouts etc.
		 IPlaceholderFolderLayout right = layout.createPlaceholderFolder(ID_FOLDER_RIGHT, IPageLayout.RIGHT, 0.66f,
	 	   editorArea);
		 right.addPlaceholder("org.globaltester.smartcardshell.ui.views.SmartCardShellView");
 
		 
	}
}
