package org.globaltester;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

public class PlatformHelper {
	
	/**
	 * Installs and starts a bundle if it's not already running
	 * @param bundleContext 
	 * @param bundleId: the Identifier (String) of the bundle to start
	 * @throws BundleException 
	 */
	public static void startBundle(String bundleId, BundleContext bundleContext) {

		Bundle bundle = getBundle(bundleId, bundleContext);

		try {
			if ((bundle != null) && (bundle.getState() != Bundle.ACTIVE)) {
				bundle.start();
			} else if (bundle == null) {
				throw new BundleException("Bundle " + bundleId + " not found. Therefore it can't be started");
			}
		} catch (BundleException e) {
			throw new RuntimeException(
					"The startup procedure could not be completed correctly with message: " + e.getMessage());
		}
	}
	
	/**
	 * Stops a bundle
	 * @param bundleContext 
	 * @param bundleId: the Identifier (String) of the bundle to stop
	 * @throws BundleException 
	 */
	public static void stopBundle(String bundleId, BundleContext bundleContext) {

		Bundle bundle = getBundle(bundleId, bundleContext);

		try {
			if (bundle != null) {
				bundle.stop();
			} else if (bundle == null) {
				throw new BundleException("Bundle " + bundleId + " not found. Therefore it can't be stopped");
			}
		} catch (BundleException e) {
			throw new RuntimeException(
					"The stop procedure could not be completed correctly with message: " + e.getMessage());
		}
	}
	
	/**
	 * @param bundleId
	 *            the id of the searched bundle
	 * @param bundleContext
	 *            context to use for searching the bundle
	 * @return true, iff the bundle for the given id could be found and has
	 *         state {@link Bundle#ACTIVE}
	 */
	public static boolean isBundleActive(String bundleId, BundleContext bundleContext){
		Bundle bundle = getBundle(bundleId, bundleContext);
		if (bundle != null){
			return bundle.getState() == Bundle.ACTIVE;
		}
		return false;
	}

	/**
	 * @param bundleId
	 *            the id of the searched bundle
	 * @param bundleContext
	 *            context to use for searching the bundle
	 * @return the bundle with the given bundleId or null if no matching bundle
	 *         is found in the given context
	 */
	public static Bundle getBundle(String bundleId, BundleContext bundleContext){
		Bundle bundle = null;
		Bundle[] bundles = bundleContext.getBundles();

		for (int i = 0; i < bundles.length; i++) {
			if (bundles[i].getSymbolicName().equals(bundleId)) {
				bundle = bundles[i];
				break;
			}
		}
		return bundle;
	}
	
	/**
	 * This method retrieves a file identifiable by a provided bundle-name and relative path
	 * @param bundleName the bundle-id of the bundle to retrieve the file from
	 * @param fileName the bundle-relative path of the file to retrieve
	 * @param bundleContext the bundle context to use
	 * @return the retrieved file
	 */
	public static File getFileFromBundle(String bundleName, String fileName, BundleContext bundleContext) {
		Bundle bundle = getBundle(bundleName, bundleContext);
		
		URL sourceBundleUrl = FileLocator.find(bundle, new Path(fileName), null);
		IPath sourceBundlePath;
		try {
			sourceBundlePath = new Path(FileLocator.toFileURL(sourceBundleUrl).getPath());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return sourceBundlePath.toFile();
	}
	
	/**
	 * This method retrieves the file that can be identified by the given parameters on the local file system.
	 * @param repoName the name of the repository
	 * @param projectName the name of the project
	 * @param fileName the path of the file relative to the project
	 * @return the identified file
	 */
	public static File getFileFromPseudoBundle(String repoName, String projectName, String fileName) {
		String absoluteRootPath = (new File("")).getAbsolutePath();
		
		int lastLineSeparatorIndex = absoluteRootPath.lastIndexOf(File.separator);
		absoluteRootPath = absoluteRootPath.substring(0, lastLineSeparatorIndex);
		lastLineSeparatorIndex = absoluteRootPath.lastIndexOf(File.separator);
		absoluteRootPath = absoluteRootPath.substring(0, lastLineSeparatorIndex);
		
		String relativePathToFile = (new File(fileName)).getPath();
		String absolutePathToFile = absoluteRootPath + File.separator + repoName + File.separator + projectName + File.separator + relativePathToFile;
		return new File(absolutePathToFile);
	}
	
	/**
	 * This method returns the contents of the provided file as byte array.
	 * Null is returned iff file can not be found or is longer than Integer.MAX_VALUE. 
	 * @param fileName the file to be read
	 * @return the contents of the file to be read
	 */
	public static byte[] readFromFile(String fileName) {
		RandomAccessFile raf = null;
		
		try {
			raf = new RandomAccessFile(fileName, "r");
			int length;
			long lengthLong = raf.length();
			if (lengthLong > Integer.MAX_VALUE) {
				raf.close();
				return null;
			} else{
				length = (int) lengthLong;
			}
			
			byte[] file = new byte[length];
			
			raf.readFully(file);
			raf.close();
			
			return file;
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			return null;
		}catch (IOException e) {
			return null;
		} finally{
			if(raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
					// do nothing
				}
			}
		}
	}
	
	/**
	 * This method writes the content of the provided byte array to the specified file.
	 * <p\>
	 * If file already exists it gets overwritten.
	 * @param fileName the file to write the provided data to
	 * @param data the data to write to the specified file
	 * @return true iff write command was successful, false otherwise
	 */
	public static boolean writeToFile(String fileName, byte[] data) {
		RandomAccessFile raf = null;
		
		File file = new File(fileName);
		File parent = file.getParentFile();
		if(parent != null) {
			parent.mkdirs();
		}
		
		if (file.exists()) {
			file.delete();
		}
		
		try {
			raf = new RandomAccessFile(file, "rw");
			raf.write(data);
			raf.close();
			return true;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}finally{
			if(raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
					// do nothing
				}
			}
		}
	}
	
}
