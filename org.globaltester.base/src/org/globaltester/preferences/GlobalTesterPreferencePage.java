package org.globaltester.preferences;

/*
 * Project GlobalTester-Plugin File GTPreferencesPage.java
 * 
 * Date 14.10.2005
 * 
 * 
 * Developed by HJP Consulting GmbH Lanfert 24 33106 Paderborn Germany
 * 
 * 
 * This software is the confidential and proprietary information of HJP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * Non-Disclosure Agreement you entered into with HJP.
 */

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.globaltester.Activator;

/**
 * This class stores the properties of the plugin
 * 
 * @version Release 2.2.0
 * @author Holger Funke
 * 
 */

public class GlobalTesterPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	// definition of field editors
	BooleanFieldEditor bfeManualSCSHSettings;
	ValidateFileFieldEditor ffeConfigFile;
	IntegerFieldEditor ifeReaderBuffer;
	RadioGroupFieldEditor rfeReadFileEOF;

	//definition of groups
	Group scshGroup;
	Group bufferGroup;

	public GlobalTesterPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("GlobalTester preferences page. Please choose a category on the menu on the left.");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {

	}

	public void init(IWorkbench workbench) {

	}

	public void propertyChange(org.eclipse.jface.util.PropertyChangeEvent event) {
		super.propertyChange(event);
		if (event.getProperty().equals(FieldEditor.VALUE)) {

			if (event.getSource() == bfeManualSCSHSettings) {
				boolean manualPath = ((Boolean) event.getNewValue())
						.booleanValue();

				ffeConfigFile.setEnabled(manualPath, scshGroup);

			}
		}
	}

	protected void performDefaults() {
		super.performDefaults();
//		boolean manualPath = Activator.getDefault().getPreferenceStore()
//				.getBoolean(PreferenceConstants.P_MANUAL_SCSH_CONF);
//
//		ffeConfigFile.setEnabled(manualPath, scshGroup);
	}

}
