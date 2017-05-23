package org.globaltester.base.ui;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.globaltester.base.PropertyElement;
import org.globaltester.base.SeverityLevel;
import org.globaltester.base.UserInteraction;

/**
 * Default implementation for interacting with a user. This uses dialogs on the
 * eclipse UI.
 * 
 * @author mboonk
 *
 */
public class UserInteractionImpl implements UserInteraction {

	@Override
	public void notify(SeverityLevel level, String message) {
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

			@Override
			public void run() {
				MessageDialog.openInformation(PlatformUI.getWorkbench().getModalDialogShellProvider().getShell(),
						"User notification", message);
			}
		});
	}

	@Override
	public int select(String message, List<PropertyElement> properties, String... allowedResults) {
		int [] result = new int [1];
		
		StringBuilder builder = new StringBuilder();

		if (properties != null && !properties.isEmpty()) {
			builder.append("Test case data:");
			for (PropertyElement element : properties) {
				if (element.getRepresentation() != null && element.getRepresentation().equals("")){
					continue;
				}
				builder.append("\n\n");
				builder.append(element.getKey() + ":\n");
				builder.append(element.getRepresentation() == null ? element.getValue() : element.getRepresentation());
			}
		}
		String messageForDialog = message + "\n\n";
		
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			
			@Override
			public void run() {
				MessageDialog md = new MessageDialog(PlatformUI.getWorkbench().getModalDialogShellProvider().getShell(),
						"User question", null, messageForDialog, MessageDialog.NONE, allowedResults, 0) {
					@Override
					protected boolean isResizable() {
						return true;
					}

					@Override
					protected Control createCustomArea(Composite parent) {
						if (builder.toString().length() > 0) {
							parent.setLayout(new FillLayout());
							Text text = new Text(parent, SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
							text.setEditable(false);
							text.setFont(JFaceResources.getTextFont());
							text.setText(builder.toString());
							return text;
						}
						return super.createCustomArea(parent);
					}
				};
				result[0] = md.open();
			}
		});
		return result[0];
	}

}
