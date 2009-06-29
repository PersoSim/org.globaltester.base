/* 
 * Project 	GlobalTester-Plugin 
 * File		PreferencesInitializer.java
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

package org.globaltester.preferences;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.globaltester.Activator;



/**
 * This class stores the properties of the plugin
 * Class used to initialize default preference values
 * 
 * @version		Release 2.2.0
 * @author 		Holger Funke
 *
 */

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * Use this to store plugin preferences
	 * For meaning of each preference look at PreferenceConstants.java
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault()
				.getPreferenceStore();
		
		store.setDefault(PreferenceConstants.P_WORKINGDIR, "");
		
		IPath pluginDir = Activator.getPluginDir();
		String configPath = pluginDir.toPortableString() + "config.js";;
		
		store.setDefault(PreferenceConstants.P_SCSH_CONF, configPath); 
		
		store.setDefault(PreferenceConstants.P_MANUAL_SCSH_CONF, false);
	}

}
