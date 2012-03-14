package org.globaltester.core.ui;

import java.util.Iterator;
import java.util.LinkedList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
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

	/**
	 * Extracts all elements of the selection that are of type defined by
	 * paramter type.
	 * 
	 * @param iSel
	 *            Selection to search in
	 * @param type
	 *            type of expected elements
	 * @return List containing all elements of given type, this might be empty
	 *         but won't be null
	 */
	@SuppressWarnings("unchecked")
	public static <T extends IResource> LinkedList<T> getSelectedIResource(ISelection iSel,
			Class<T> type) {
		LinkedList<T> selectedResources = new LinkedList<T>();

		// check type of selection
		if (iSel instanceof IStructuredSelection) {
			Iterator<?> selectionIter = ((IStructuredSelection) iSel)
					.iterator();
			while (selectionIter.hasNext()) {
				Object curElem = (Object) selectionIter.next();
				if (type.isInstance(curElem)) {
					selectedResources.add((T) curElem);
				}
			}
		}

		return selectedResources;
	}

}
