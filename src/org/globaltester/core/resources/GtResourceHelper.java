package org.globaltester.core.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
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
			}
		}

	}

	/**
	 * copy all files from an installed plugin into a new project
	 * 
	 * @param currentScriptPlugin
	 * @param project
	 * @throws IOException
	 */
	public static void copyPluginContent2WorkspaceProject(
			String currentScriptPlugin, IProject project) throws IOException {

		// get source path
		Bundle curBundle = Platform.getBundle(currentScriptPlugin);
		URL url = FileLocator.find(curBundle, new Path("/"), null);
		IPath pluginDir = new Path(FileLocator.toFileURL(url).getPath());

		// define files to be copied
		File source = pluginDir.toFile();
		File destination = project.getLocation().toFile();
		// TODO make sure that all contained/required files are copied
		String[] children = new String[] { "TestCases" };

		// copy files
		for (int i = 0; i < children.length; i++) {
			copyFiles(new File(source, children[i]), new File(destination,
					children[i]));
		}

		// refresh workspace

	}

	private static void copyFiles(File sourceLocation, File targetLocation)
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
}
