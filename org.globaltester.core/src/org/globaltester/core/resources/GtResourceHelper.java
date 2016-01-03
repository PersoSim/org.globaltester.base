package org.globaltester.core.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.globaltester.core.Activator;
import org.globaltester.logging.logger.GtErrorLogger;
import org.osgi.framework.Bundle;

public class GtResourceHelper {
	/**
	 * create an resource together with all parents
	 * 
	 * @param resource
	 * @throws CoreException
	 */
	public static void createWithAllParents(IResource resource)
			throws CoreException {
		if (resource.exists()) {
			return;
		} else {
			// make sure parent exists
			IContainer parent = resource.getParent();
			createWithAllParents(parent);

			if (resource instanceof IFile) {
				((IFile) resource).create(null, false, null);
			} else if (resource instanceof IFolder) {
				((IFolder) resource).create(false, true, null);
			} else if (resource instanceof IProject) {
				((IProject) resource).create(null);
				((IProject) resource).open(null);
			}
		}

	}
	
	/**
	 * copy files from an installed plugin into a new project
	 * 
	 * @param currentScriptPlugin
	 * @param project
	 * @param pathToFiles
	 * @param toCopy
	 * @throws IOException
	 * @throws CoreException 
	 */
	public static void copyPluginFilesToWorkspaceProject(String currentScriptPlugin, IProject project, String pathToFiles, String ... toCopy) throws IOException{
		// get source path
		Bundle curBundle = Platform.getBundle(currentScriptPlugin);
		URL url = FileLocator.find(curBundle, new Path(pathToFiles), null);
		IPath pluginDir = new Path(FileLocator.toFileURL(url).getPath());
		
		// define files to be copied
		File source = pluginDir.toFile();
		File destination = project.getLocation().toFile();
		
		// copy files
		
		for (String filename : toCopy){
			copyFiles(new File(source, filename), new File(destination, filename));
		}
		// refresh the project
		try {
			project.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			// refresh of project failed
			// relevant CoreException will be in the eclipse log anyhow
			// users most probably will ignore this behavior and refresh manually 
		}
	}

    /**
     * Copy from an input to an output stream. Both streams remain open.
     * @param input
     * @param output
     * @throws IOException
     */
    public static void copyStream(InputStream input, OutputStream output) throws IOException {
        int bytesRead;
        byte [] buffer = new byte [1024 * 1024];
        while ((bytesRead = input.read(buffer))!= -1) {
            output.write(buffer, 0, bytesRead);
        }
    }
	
	public static void copyFile(InputStream source, File targetLocation) throws IOException{
		if (!targetLocation.exists()){
			targetLocation.createNewFile();
		}
		if (targetLocation.isFile() && targetLocation.canWrite()){
			FileOutputStream output = new FileOutputStream(targetLocation);
			copyStream(source, output);
		}
	}
	
	public static void copyFiles(File sourceLocation, File targetLocation)
			throws IOException {

		if (sourceLocation.isDirectory()) {
			if (!targetLocation.exists()) {
				if (!targetLocation.mkdir()) {
					throw new IOException("Target directory \""
							+ targetLocation.getAbsolutePath()
							+ "\" can not be created.");
				}
			}

			String[] children = sourceLocation.list();
			for (int i = 0; i < children.length; i++) {
				copyFiles(new File(sourceLocation, children[i]), new File(
						targetLocation, children[i]));
			}
		} else {

			InputStream in = null;
			OutputStream out = null;

			try {
				in = new FileInputStream(sourceLocation);
				out = new FileOutputStream(targetLocation);

				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
			} finally {
				try {
					if (in != null) {
						in.close();
					}
				} finally {
					if (out != null) {
						out.close();
					}
				}
			}
		}
	}

	public static void createFolder(IFolder folder) throws CoreException {
		IContainer parent = folder.getParent();
		if (parent instanceof IFolder) {
			createFolder((IFolder) parent);
		}
		if (!folder.exists()) {
			folder.create(false, true, null);
		}
	}

	/**
	 * Create a folder structure from given paths.
	 * 
	 * @param project
	 *            project to create the folders inside
	 * @param paths
	 *            array of relative paths of the folders to be created
	 * @throws CoreException
	 */
	public static void addToProjectStructure(IProject project, String[] paths)
			throws CoreException {
		for (String currentPath : paths) {
			IFolder currentFolder = project.getFolder(currentPath);
			createFolder(currentFolder);
		}
	}

	/**
	 * Add the given nature to the project.
	 * 
	 * @param project
	 *            project to add the nature to
	 * @param natureID
	 *            Id of nature to add
	 * @throws CoreException
	 */
	public static void addNature(IProject project, String natureID)
			throws CoreException {
		if (!project.hasNature(natureID)) {
			IProjectDescription description = project.getDescription();
			String[] prevNatures = description.getNatureIds();
			String[] newNatures = new String[prevNatures.length + 1];
			System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
			newNatures[prevNatures.length] = natureID;
			description.setNatureIds(newNatures);

			IProgressMonitor monitor = null;
			project.setDescription(description, monitor);
		}
	}

	/**
	 * Create an empty project
	 * 
	 * @param projectName
	 *            name of the project to be created
	 * @param location
	 *            location where the project shall be created. If empty the
	 *            default workspace location will be used.
	 * 
	 */
	public static IProject createEmptyProject(String projectName, URI location) {
		IProject newProject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName);

		if (!newProject.exists()) {
			URI projectLocation = location;
			IProjectDescription desc = newProject.getWorkspace()
					.newProjectDescription(newProject.getName());
			if (location != null
					&& ResourcesPlugin.getWorkspace().getRoot()
							.getLocationURI().equals(location)) {
				projectLocation = null;
			}

			desc.setLocationURI(projectLocation);
			try {
				newProject.create(desc, null);
				if (!newProject.isOpen()) {
					newProject.open(null);
				}
			} catch (CoreException e) {
				GtErrorLogger.log(Activator.PLUGIN_ID, e);
			}
		}

		return newProject;
	}

	/**
	 * Create an empty project
	 * 
	 * @param projectName
	 *            name of the project to be created
	 * @param location
	 *            location where the project shall be created. If empty the
	 *            default workspace location will be used.
	 * 
	 */
	public static Set<String> getProjectNamesWithNature(String natureId) {
		IProject[] availableProjects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();
		HashSet<String> returnSet = new HashSet<String>();

		for (IProject curProject : availableProjects) {
			try {
				if (curProject.hasNature(natureId)) {
					returnSet.add(curProject.getName());
				}
			} catch (CoreException e) {
				GtErrorLogger.log(Activator.PLUGIN_ID, e);
			}
		}

		return returnSet;
	}

	/**
	 * Return an IFile if the given absolute path points to a location under any existing project.
	 * 
	 * @param path
	 * @return
	 */
	public static IFile getIFileForLocation(String path) {
		return ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(path));
	}
}
