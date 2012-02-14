package org.globaltester.core.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.globaltester.logging.logger.GtErrorLogger;

public class GtUiHelper {
	
	public static void openInEditor(IFile file) {

		try {
			IWorkbenchPage page = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();

			IDE.openEditor(page, file);
		} catch (PartInitException e) {
			// log Exception to eclipse log
			GtErrorLogger.log(Activator.PLUGIN_ID, e);

			// users most probably will ignore this behavior and open editor
			// manually, so do not open annoying dialog
		}
	}

}
