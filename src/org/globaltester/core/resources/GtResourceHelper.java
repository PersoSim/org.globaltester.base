package org.globaltester.core.resources;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

public class GtResourceHelper {
	/**
	 * create an resource together with all parents
	 * @param resource
	 * @throws CoreException
	 */
	public static void createWithAllParents(IResource resource) throws CoreException {
		if (resource.exists()) {
			return;
		} else {
			//make sure parent exists
			IContainer parent = resource.getParent();
			createWithAllParents(parent);
			
			if (resource instanceof IFile) {
				((IFile)resource).create(null, false, null);
			} else if (resource instanceof IFolder) {
				((IFolder)resource).create(false, true, null);
			}  else if (resource instanceof IProject) {
				((IProject)resource).create(null);
			} 
		}
		
	}
}
