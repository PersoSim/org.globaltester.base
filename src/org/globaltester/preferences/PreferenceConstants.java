/* 
 * Project 	GlobalTester-Plugin 
 * File		PreferencesConstants.java
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

/**
 * This class stores the properties of the plugin
 * Constant definitions for plug-in preferences
 * 
 * @version		Release 2.1.1
 * @author 		Holger Funke
 *
 */

public class PreferenceConstants {

	// working directory of current project
	public static final String P_WORKINGDIR = "pathPreference";

	// path to configuration file needed by smart card shell
	public static final String P_CONFIGJS = "pathConfigJs";
	
	// name of card reader
	public static final String P_READER = "reader";
	
	// directory name of logging files
	public static final String P_LOGGINGDIR = "pathLogging";
	
	// directory name of report files
	public static final String P_REPORTDIR = "pathReports";
	
	// switch to set manual directory settings
	public static final String P_MANUALDIRSETTINGS = "manualDirSettings";
	
	// switch to manual card reader settings
	public static final String P_MANUALREADERSETTINGS = "manualReaderSettings";
	
	// file name of csca certificate
	//public static final String P_CSCACERT = "cscaCert";

	// file name of csca certificate
	//public static final String P_DSCERT = "dsCert";

	// log level of log file
	public static final String P_LOGLEVEL = "Logging level";

	// switch to use swipe reader
	//public static final String P_USEMRZREADER = "Use MRZ Reader";
	
	// switch to use html log file
	public static final String P_HTMLLOGGING = "HTML Logging";
	
	// switch to use plain log file
	public static final String P_PLAINLOGGING = "Plain Logging";
	
	// path to property file
	public static final String P_PROPERTYFILE = "Property File";

	// date when report directory was created
	public static final String P_REPORTDIRDATE = "Report dir with date";
	
	public static final String P_REMOTETESTING = "Use remote testing ";
	public static final String P_REMOTETESTINGSERVER = "IP address of remote server";

	//Name of card reader
	public static final String P_CARDREADERNAME = "Card reader name";
	
	public static final String P_USEISO8601LOGGING = "Use ISO 8601 Logging";
	
	public static final String P_LASTMRZSELECTION = "Last selection of MRZ history";
	
	//public static final String P_MRZ1 = "Default definition of first line in MRZ";

	//public static final String P_MRZ2 = "Default definition of second line in MRZ";
	
	//public static final String P_READBUFFER = "Default read buffer size";
	
	public static final String P_AUTOMATICREPORT = "Generate report automatically when test is finished";
	
	// CSV report
	public static final String P_CSVSAMPLEID = "Sample ID for CSV reports";
	public static final String P_CSVPLATFORMID = "Platform ID for CSV reports";
	
	//public static final String P_BUFFERREADFILEEOF = "Buffer size for function readFileEOF";
	
}
