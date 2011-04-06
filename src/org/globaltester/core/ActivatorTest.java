package org.globaltester.core;

import static org.junit.Assert.*;

import org.junit.Test;
import org.osgi.framework.BundleContext;

public class ActivatorTest {

	/**
	 * Check that the Activator returns non-null context
	 */
	@Test
	public void testGetContext() {
		System.out.println("abab");
		BundleContext context = Activator.getContext();
		assertNotNull("BundleContext of org.globaltester.core is null", context);
	}

}
