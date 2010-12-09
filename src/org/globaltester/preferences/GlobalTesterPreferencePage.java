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
import org.globaltester.logging.preferences.ValidateFileFieldEditor;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.globaltester.Activator;
import org.globaltester.logger.GTLogger;

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
		setDescription("GlobalTester preferences page.");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {

		Composite container = new Composite(this.getFieldEditorParent(),
				SWT.NONE);
		GridData containerData = new GridData(GridData.FILL, GridData.FILL,
				true, false);
		containerData.horizontalSpan = 1;

		container.setLayoutData(containerData);
		GridLayout layout = new GridLayout(1, false);
		container.setLayout(layout);

		scshGroup = new Group(container, SWT.NONE);
		scshGroup.setText("SmartCardShell configuration");
		GridData gd = new GridData(GridData.FILL, GridData.FILL, true, false);
		gd.horizontalSpan = 2;
		scshGroup.setLayoutData(gd);
		scshGroup.setLayout(new GridLayout(2, false));

		// manual settings of directories
		bfeManualSCSHSettings = new BooleanFieldEditor(
				PreferenceConstants.P_MANUAL_SCSH_CONF,
				"Manual select configuration file for SCSH", scshGroup);

		addField(bfeManualSCSHSettings);

		// configuration file for smart card shell
		ffeConfigFile = new ValidateFileFieldEditor(PreferenceConstants.P_SCSH_CONF,
				"SCSH Config file:", scshGroup);

		if (!Activator.getDefault().getPreferenceStore().getBoolean(
				PreferenceConstants.P_MANUAL_SCSH_CONF)) {
			ffeConfigFile.setEnabled(false, scshGroup);
		}
		addField(ffeConfigFile);

		
		bufferGroup = new Group(container, SWT.NONE);
		bufferGroup.setText("Buffer");
		GridData gd3 = new GridData(GridData.FILL, GridData.FILL, true, false);
		gd3.horizontalSpan = 2;
		bufferGroup.setLayoutData(gd3);
		bufferGroup.setLayout(new GridLayout(2, false));

		ifeReaderBuffer = new IntegerFieldEditor(
				PreferenceConstants.P_READBUFFER, "Read buffer size:",
				bufferGroup);
		ifeReaderBuffer.setValidRange(0, 4096);
		addField(ifeReaderBuffer);

		rfeReadFileEOF = new RadioGroupFieldEditor(
				PreferenceConstants.P_BUFFERREADFILEEOF,
				"Alternative ways for JavaScript function readFileEOF()",
				1,
				new String[][] {
						new String[] {
								"Read data groups by checking header information (fast)",
								"INFINITE" },
						new String[] {
								"Read data groups byte by byte (very slow)",
								"SMALL" } }, bufferGroup, true);
		addField(rfeReadFileEOF);
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
		GTLogger.getInstance().debug("Switched GT Preference Page back do default values");
		boolean manualPath = Activator.getDefault().getPreferenceStore()
				.getBoolean(PreferenceConstants.P_MANUAL_SCSH_CONF);

		ffeConfigFile.setEnabled(manualPath, scshGroup);
	}

}