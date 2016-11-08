package org.globaltester;

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
}
