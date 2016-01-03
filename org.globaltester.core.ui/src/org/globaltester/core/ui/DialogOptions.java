package org.globaltester.core.ui;

/**
 * Provides a means to set the possible options for File- and DirectoryDialogs,
 * which will be used by GtUiHelper to instantiate these dialogs with the needed values.
 * 
 * @author mboonk
 *
 */
public class DialogOptions {
	String message = "", filterPath = "", fileName = "", text = "";  //$NON-NLS-1$//$NON-NLS-2$
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	String directoryPath;
	String [] filterNames = new String [0];
	String [] filterExtensions = new String [0];
	String [] fileNames = new String [0];
	int filterIndex = 0;
	boolean overwrite = false;
	
	public String getMessage() {
		return message;
	}
	
	public String getFilterPath() {
		return filterPath;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public String getDirectoryPath() {
		return directoryPath;
	}
	
	public String[] getFilterNames() {
		return filterNames;
	}
	
	public String[] getFilterExtensions() {
		return filterExtensions;
	}
	
	public String[] getFileNames() {
		return fileNames;
	}
	
	public int getFilterIndex() {
		return filterIndex;
	}
	
	public boolean getOverwrite() {
		return overwrite;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setFilterPath(String filterPath) {
		this.filterPath = filterPath;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public void setDirectoryPath(String directoryPath) {
		this.directoryPath = directoryPath;
	}
	
	public void setFilterNames(String[] filterNames) {
		this.filterNames = filterNames;
	}
	
	public void setFilterExtensions(String[] filterExtensions) {
		this.filterExtensions = filterExtensions;
	}
	
	public void setFileNames(String[] fileNames) {
		this.fileNames = fileNames;
	}
	
	public void setFilterIndex(int filterIndex) {
		this.filterIndex = filterIndex;
	}
	
	public void setOverwrite(boolean overwrite) {
		this.overwrite = overwrite;
	}
}
