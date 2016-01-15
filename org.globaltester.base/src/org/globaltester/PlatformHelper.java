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

		Bundle bundle = null;
		Bundle[] bundles = bundleContext.getBundles();

		for (int i = 0; i < bundles.length; i++) {
			if (bundles[i].getSymbolicName().equals(bundleId)) {
				bundle = bundles[i];
				break;
			}
		}

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
}
