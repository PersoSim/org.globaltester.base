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
 * @version		Release 2.1.1
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
		store.setDefault(PreferenceConstants.P_REPORTDIR, "");
		//store.setDefault(PreferenceConstants.P_CSCACERT, "");
		//store.setDefault(PreferenceConstants.P_DSCERT, "");
		store.setDefault(PreferenceConstants.P_PROPERTYFILE, "");
		
		IPath pluginDir = Activator.getPluginDir();
		String configPath = pluginDir.toPortableString() + "config.js";;
		
		store.setDefault(PreferenceConstants.P_CONFIGJS, configPath); 
		//store.setDefault(PreferenceConstants.P_USEMRZREADER, true);
		store.setDefault(PreferenceConstants.P_REPORTDIRDATE, "");
		store.setDefault(PreferenceConstants.P_CARDREADERNAME, "unknown");
		store.setDefault(PreferenceConstants.P_LASTMRZSELECTION, -1);
		//store.setDefault(PreferenceConstants.P_MRZ1, "P<UTOERIKSSON<<ANNA<MARIA<<<<<<<<<<<<<<<<<<<");
		//store.setDefault(PreferenceConstants.P_MRZ2, "L898902C<3UTO6908061F9406236ZE184226B<<<<<14");
		//store.setDefault(PreferenceConstants.P_READBUFFER, 223);
		store.setDefault(PreferenceConstants.P_AUTOMATICREPORT, false);
		
		store.setDefault(PreferenceConstants.P_CSVSAMPLEID, "12345");
		store.setDefault(PreferenceConstants.P_CSVPLATFORMID, "00");
		
		//store.setDefault(PreferenceConstants.P_BUFFERREADFILEEOF, "INFINITE");
	}

}
