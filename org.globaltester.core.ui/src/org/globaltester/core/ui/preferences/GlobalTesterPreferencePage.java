package org.globaltester.core.ui.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.globaltester.core.Activator;

public class GlobalTesterPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	public GlobalTesterPreferencePage() {
		IPreferenceStore store = new ScopedPreferenceStore(new InstanceScope(),
				Activator.PLUGIN_ID);
		setPreferenceStore(store);
		setDescription("GlobalTester preferences page.");
	}

	@Override
	protected void createFieldEditors() {

	}

	public void init(IWorkbench workbench) {

	}

}