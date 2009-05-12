package org.globaltester.preferences;
/* 
 * Project 	GlobalTester-Plugin 
 * File		GTPreferencesPage.java
 *
 * Date    	14.10.2005
 * 
 * 
 * Developed by HJP Consulting GmbH
 * Lanfert 24
 * 33106 Paderborn
 * Germany
 * 
 * 
 * This software is the confidential and proprietary information
 * of HJP ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance 
 * with the terms of the Non-Disclosure Agreement you entered 
 * into with HJP.
 * 
 */

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.globaltester.Activator;



/**
 * This class stores the properties of the plugin
 * 
 * @version		Release 2.1.1
 * @author 		Holger Funke
 *
 */
//FIXME AM reorganize Preference Pages and their contents 

public class GlobalTesterPreferencePage extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	// definition of field editors
	BooleanFieldEditor bfeManualReaderSettings;
	FileFieldEditor ffePropertyFile;
	RadioGroupFieldEditor rgfe;
	BooleanFieldEditor bfeManualPathSettings;
	DirectoryFieldEditor dfeReportDir;
	FileFieldEditor ffeConfigFile;
	BooleanFieldEditor bfeAutomaticReport;
	
	//definition of groups
	Group directoryGroup;
	Group readerGroup;
	
	Group reportGroup;
	
	public GlobalTesterPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("GlobalTester preferences page.");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {

		Composite container = new Composite(this.getFieldEditorParent(),SWT.NONE);
		GridData containerData = new GridData(GridData.FILL,GridData.FILL,true,false);
		containerData.horizontalSpan = 1;

		container.setLayoutData(containerData);
		GridLayout layout = new GridLayout(1,false);
		container.setLayout(layout);
		
		
		directoryGroup = new Group(container, SWT.NONE);
		directoryGroup.setText("Directories and files");
		GridData gd = new GridData(GridData.FILL,GridData.FILL,true,false);
		gd.horizontalSpan = 2;
		directoryGroup.setLayoutData(gd);
		directoryGroup.setLayout(new GridLayout(2,false));
		
		// manual settings of directories
		bfeManualPathSettings = new BooleanFieldEditor(
				PreferenceConstants.P_MANUALDIRSETTINGS ,
				"Manual setting of directories and files",
				directoryGroup);
		

		addField(bfeManualPathSettings);
		
		// report directory
		dfeReportDir = new DirectoryFieldEditor(PreferenceConstants.P_REPORTDIR, 
				"Report directory:", directoryGroup);

		if (!Activator.getDefault().getPreferenceStore().getBoolean(
				PreferenceConstants.P_MANUALDIRSETTINGS)) {		
			dfeReportDir.setEnabled(false, directoryGroup);
		}		
		addField(dfeReportDir);

		// config file
		ffeConfigFile = new FileFieldEditor(PreferenceConstants.P_CONFIGJS, 
				"SCSH Config file:", directoryGroup);

		if (!Activator.getDefault().getPreferenceStore().getBoolean(
				PreferenceConstants.P_MANUALDIRSETTINGS)) {		
			ffeConfigFile.setEnabled(false, directoryGroup);
		}		
		addField(ffeConfigFile);

		
		// property file
		ffePropertyFile = new FileFieldEditor(PreferenceConstants.P_PROPERTYFILE, 
				"Property file:", directoryGroup);
		
		addField(ffePropertyFile);
		
	}

	public void init(IWorkbench workbench) {

	}

	public void propertyChange(org.eclipse.jface.util.PropertyChangeEvent event) {
		super.propertyChange(event);
		if (event.getProperty().equals(FieldEditor.VALUE)) {
            
			if (event.getSource() == bfeManualReaderSettings) {
				boolean manualReader = ((Boolean)event.getNewValue()).booleanValue();
				if (manualReader) {
					rgfe.setEnabled(true, readerGroup);					
				}
				else {
					rgfe.setEnabled(false, readerGroup);
				}
			}

			if (event.getSource() == bfeManualPathSettings) {
				boolean manualPath = ((Boolean)event.getNewValue()).booleanValue();
				if (manualPath) {
					dfeReportDir.setEnabled(true, directoryGroup);
					ffeConfigFile.setEnabled(true, directoryGroup);
				}
				else {
					dfeReportDir.setEnabled(false, directoryGroup);
					ffeConfigFile.setEnabled(false, directoryGroup);
				}
			}
		}
	}

}