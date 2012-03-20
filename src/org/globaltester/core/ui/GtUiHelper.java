package org.globaltester.core.ui;

import java.util.Iterator;
import java.util.LinkedList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.wizards.IWizardDescriptor;
import org.eclipse.ui.wizards.IWizardRegistry;
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
	public static <T extends IResource> LinkedList<T> getSelectedIResource(
			ISelection iSel, Class<T> type) {
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

	/**
	 * Searches all wizard registries to find a wizard matching the given ID and
	 * opens it.
	 * 
	 * @param string
	 * @throws CoreException
	 */
	public static void openWizard(String wizardId) throws CoreException {
		IWizardDescriptor descriptor = findNewWizard(wizardId);
		if (descriptor == null) {
			descriptor = findImportWizard(wizardId);
		}
		if (descriptor == null) {
			descriptor = findExportWizard(wizardId);
		}

		openWizard(descriptor);

	}

	private static IWizardDescriptor findNewWizard(String wizardId) {
		IWizardRegistry registry = PlatformUI.getWorkbench()
				.getNewWizardRegistry();
		return registry.findWizard(wizardId);
	}

	private static IWizardDescriptor findImportWizard(String wizardId) {
		IWizardRegistry registry = PlatformUI.getWorkbench()
				.getImportWizardRegistry();
		return registry.findWizard(wizardId);
	}

	private static IWizardDescriptor findExportWizard(String wizardId) {
		IWizardRegistry registry = PlatformUI.getWorkbench()
				.getExportWizardRegistry();
		return registry.findWizard(wizardId);
	}

	private static void openWizard(IWizardDescriptor descriptor)
			throws CoreException {
		if (descriptor != null) {
			IWizard wizard = descriptor.createWizard();
			WizardDialog wd = new WizardDialog(PlatformUI.getWorkbench()
					.getDisplay().getActiveShell(), wizard);
			wd.setTitle(wizard.getWindowTitle());
			wd.open();
		}
	}

	public static void openErrorDialog(Shell shell, String msg) {
		MessageBox dialog = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.ICON_ERROR);
		dialog.setMessage(msg);
		dialog.open();
		
	}

}
