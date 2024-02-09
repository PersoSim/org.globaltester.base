package org.globaltester.base.resources;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.globaltester.logging.BasicLogger;
import org.globaltester.logging.tags.LogLevel;
import org.osgi.framework.Bundle;

public class GtResourceHelper {
	
	private GtResourceHelper() {
		// hide implicit public constructor
	}
	
	/**
	 * create an resource together with all parents
	 * 
	 * @param resource
	 * @throws CoreException
	 */
	public static void createWithAllParents(IResource resource)
			throws CoreException {
		if (!resource.exists()) {
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
	 * copy files from a given source to a given project
	 * 
	 * @param IContainer the IContainer of the destination
	 * @param sourceBundleRoot the (absolute) root of the source
	 * @param filter file names to filter (without path)
	 * @param filesToCopy the source file names (without path)
	 * @throws IOException
	 * @throws CoreException 
	 */
	public static void copySelectedFilesToWorkspaceProject(IContainer destination, File sourceBundleRoot, Collection<String> filter, String ... filesToCopy) throws IOException{
		File destinationBundleRoot = destination.getLocation().toFile();
		
		// copy files
		
		if((filesToCopy == null) || (filesToCopy.length == 0)) {
			filesToCopy = sourceBundleRoot.list();
		}
		
		for (String currentFilename : filesToCopy){
			if (filter.contains(currentFilename)) {
				continue;
			}
			copyFiles(new File(sourceBundleRoot, currentFilename), new File(destinationBundleRoot, currentFilename));
		}
		
		// refresh the project
		try {
			destination.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			// refresh of project failed
			// relevant CoreException will be in the eclipse log anyhow
			// users most probably will ignore this behavior and refresh manually 
		}
	}
	
	/**
	 * copy files from an installed plugin into a new project
	 * 
	 * @param sourceBundleSymbolicName
	 *            the Bundle Symbolic Name of the source bundle
	 * @param destinationProject
	 *            the IProject of the destination project
	 * @param pathRelativeToSourceBundleRoot
	 *            the absolute path to the source files
	 * @param filter
	 *            file names to filter (without path)
	 * @param filesToCopy
	 *            the source file names (without path)
	 * @throws IOException
	 * @throws CoreException
	 * @throws RuntimeException
	 *             when the data to be copied can not be found in the given
	 *             bundle
	 */
	public static void copyPluginFilesToWorkspaceProject(String sourceBundleSymbolicName, IContainer destinationProject, String pathRelativeToSourceBundleRoot, Collection<String> filter, String ... filesToCopy) throws IOException{
		// get source path
		Bundle sourceBundle = Platform.getBundle(sourceBundleSymbolicName);
		URL sourceBundleUrl = FileLocator.find(sourceBundle, new Path(pathRelativeToSourceBundleRoot), null);
		if (sourceBundleUrl == null){
			throw new RuntimeException("Files in " + pathRelativeToSourceBundleRoot + " in bundle " + sourceBundleSymbolicName + " not found. ");
		}
		IPath sourceBundlePath = new Path(FileLocator.toFileURL(sourceBundleUrl).getPath());
		
		// define files to be copied
		File sourceBundleRoot = sourceBundlePath.toFile();
		
		copySelectedFilesToWorkspaceProject(destinationProject, sourceBundleRoot, filter, filesToCopy);
	}
	
	/**
	 * copy files from an installed plugin into a given IContainer
	 * 
	 * @see #copyPluginFilesToWorkspaceProject(String, IContainer, String, Collection, String...)
	 * @throws IOException
	 */
	public static void copyPluginFilesToWorkspaceProject(String sourceBundleSymbolicName, IContainer destinationProject, String pathRelativeToSourceBundleRoot, String ... filesToCopy) throws IOException{
		copyPluginFilesToWorkspaceProject(sourceBundleSymbolicName, destinationProject, pathRelativeToSourceBundleRoot, Collections.emptySet(), filesToCopy);
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
			Files.copy(source, targetLocation.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
	}
	
	/**
	 * Copies files recursively from sourceLocation to targetLocation.
	 *
	 * @param sourceLocation
	 * @param targetLocation
	 * @param skipChilds names of children to be skipped (on the first layer)
	 * @throws IOException
	 */
	public static void copyFiles(File sourceLocation, File targetLocation, String... skipChilds)
			throws IOException {
		
		if(!sourceLocation.exists()) {
			BasicLogger.log("File " + sourceLocation.toString() + " not found", LogLevel.ERROR);
			return; // prevents FileNotFoundException if dir/file does not exist
		}
		
		if (sourceLocation.isDirectory()) {
			if (!targetLocation.exists() && !targetLocation.mkdir()) {
					throw new IOException("Target directory \""
							+ targetLocation.getAbsolutePath()
							+ "\" can not be created.");
			}

			String[] children = sourceLocation.list();
			for (String curChild : children) {
				if (Arrays.asList(skipChilds).contains(curChild)) {
					continue;
				}
				copyFiles(new File(sourceLocation, curChild), new File(
						targetLocation, curChild));
			}
		} else {
			//System.out.println("sourceLocation Path: " + sourceLocation.toPath().toString());
			//System.out.println("targetLocation Path: " + targetLocation.toPath().toString());
			for (int i = 0; i < 3; i++)
			{
				try (InputStream inputStream = Files.newInputStream(sourceLocation.toPath())) {
					Files.copy(inputStream, targetLocation.toPath(), StandardCopyOption.REPLACE_EXISTING);
					break;
				} catch (FileSystemException e)	{
					if (i == 2)
						throw e;
					else {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
							Thread.currentThread().interrupt();
						}
					}
				}
			}
		}
	}
	
	/**
	 * This method performs batch copying of files to a single destination directory.
	 * Single copy operations are performed by {@link #copyFiles(File, File)} method.
	 * @param sourceFiles the source files to copy
	 * @param destinationDir the destination directory
	 * @throws IOException 
	 */
	public static void copyFilesToDir(List<String> sourceFiles, String destinationDir) throws IOException {
		File fileFrom;
		File fileTo;
		for(String currentFileName:sourceFiles) {
			if (currentFileName == null){
				continue;
			}
			
			fileFrom = new File(currentFileName);
			
			if(!(fileFrom.exists() && fileFrom.isFile())) {
				continue;
			}
			
			fileTo = new File(destinationDir, fileFrom.getName());
			
			copyFiles(fileFrom, fileTo);
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
					e.printStackTrace();
					// #833 Use proper logging mechanism
					//log(LogService.LOG_ERROR, "CoreException in "+Activator.PLUGIN_ID, e);
				
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
		HashSet<String> returnSet = new HashSet<>();

		for (IProject curProject : availableProjects) {
			try {
				if (curProject.hasNature(natureId)) {
					returnSet.add(curProject.getName());
				}
			} catch (CoreException e) {
				e.printStackTrace();
				// #833 Use proper logging mechanism
				//log(LogService.LOG_ERROR, "CoreException in "+Activator.PLUGIN_ID, e);
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
	
	/**
	 * This method recursively prints the structure of the provided IResource
	 * 
	 * @param iResource the IResource to be printed
	 */
	public static void listContentOfIresource(IResource iResource) {
		listContentOfIresource(iResource, "");
	}
	
	/**
	 * This method recursively prints the structure of the provided IResource
	 * 
	 * @param iResource the IResource to be printed
	 * @param prefix the prefix to add to each new sub level
	 */
	private static void listContentOfIresource(IResource iResource, String prefix) {
		
		IResource[] members = null;
		
		try {
			switch (iResource.getType()) {
			case IResource.ROOT:
				IWorkspaceRoot root = (IWorkspaceRoot) iResource;
				System.out.println(prefix + "root: " + root.getName());
				members = root.members();
				break;
			case IResource.PROJECT:
				IProject iProject = (IProject) iResource;
				System.out.println(prefix + "project: " + iProject.getName());
				members = iProject.members();
				break;
			case IResource.FOLDER:
				IFolder iFolder = (IFolder) iResource;
				System.out.println(prefix + "folder: " + iFolder.getName());
				members = iFolder.members();
				break;
			case IResource.FILE:
				IFile iFile = (IFile) iResource;
				System.out.println(prefix + "file: " + iFile.getName());
				return;
			default:
				break;
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(members != null) {
			prefix += "   ";
			for(IResource currentIResource : members) {
				listContentOfIresource(currentIResource, prefix);
			}
		}
	}
	
	public static byte[] readFileFromBundle(String bundleSymbolicName, String path) throws IOException {
		Bundle bundle = Platform.getBundle(bundleSymbolicName);
		URL url = bundle.getEntry(path);
		//URL url = new URL("platform:/plugin/"+ bundleSymbolicName + "/" + path);
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		
		
		try (InputStream in = url.openConnection().getInputStream()) {
			while (true) {
				int r = in.read(buffer);
				if (r == -1) break;
				out.write(buffer, 0, r);
			}
		}
		
		return out.toByteArray();
	}
}
