package org.globaltester.base;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.BackingStoreException;

/**
 * Helper class providing methods for accessing eclipse preferences.
 * 
 * @author mboonk
 *
 */
public class PreferenceHelper {
	private static IScopeContext context = InstanceScope.INSTANCE;
	private static IScopeContext contextDefault = DefaultScope.INSTANCE;

	/**
	 * Read the preference value from the {@link InstanceScope} of the given
	 * bundle.
	 * 
	 * @param bundle
	 *            the bundle ID to use
	 * @param key
	 *            the preference key
	 * @return the value of the preference or <code>null</code> if not existent
	 */
	public static String getPreferenceValue(String bundle, String key) {
		return getPreferenceValue(bundle, key, null);
	}

	/**
	 * Read the preference value from the {@link InstanceScope} of the given
	 * bundle.
	 * 
	 * @param bundle
	 *            the bundle ID to use
	 * @param key
	 *            the preference key
	 * @param defaultValue
	 *            the default value returned when bundle/key can not be found
	 * @return the value of the preference or defaultValue if not existent
	 */
	public static String getPreferenceValue(String bundle, String key, String defaultValue) {
		IEclipsePreferences preferences = context.getNode(bundle);
		String candidate = preferences.get(key, contextDefault.getNode(bundle).get(key, defaultValue));
		return candidate;
	}
	
	
	/**
	 * Set the preference default value from the {@link DefaultScope} of the given
	 * bundle.
	 * 
	 * @param bundle
	 *            the bundle ID to use
	 * @param key
	 *            the preference key
	 * @param value
	 *            the {@link String} representation of the value to set
	 * @return the value of the preference or <code>null</code> if not existent
	 */
	public static void setPreferenceDefaultValue(String bundle, String key, String value) {
		IEclipsePreferences preferences = contextDefault.getNode(bundle);
		preferences.put(key, value);
	}
	
	
	/**
	 * Set the preference value from the {@link InstanceScope} of the given
	 * bundle.
	 * 
	 * @param bundle
	 *            the bundle ID to use
	 * @param key
	 *            the preference key
	 * @param value
	 *            the {@link String} representation of the value to set
	 * @return the value of the preference or <code>null</code> if not existent
	 */
	public static void setPreferenceValue(String bundle, String key, String value) {
		IEclipsePreferences preferences = context.getNode(bundle);
		preferences.put(key, value);
	}
	
	
	/**
	 * Remove the preference value from the {@link InstanceScope} of the given
	 * bundle.
	 * 
	 * @param bundle
	 *            the bundle ID to use
	 * @param key
	 *            the preference key
	 * @param value
	 *            the {@link String} representation of the value to set
	 * @return the value of the preference or <code>null</code> if not existent
	 */
	public static void unsetPreferenceValue(String bundle, String key) {
		IEclipsePreferences preferences = context.getNode(bundle);
		preferences.remove(key);
	}
	
	/**
	 * Flush the preferences to the backingstore for a given bundle
	 * @param bundle
	 */
	public static void flush(String bundle) {
		IEclipsePreferences preferences = context.getNode(bundle);
		try {
			preferences.flush();
		} catch (BackingStoreException e) {
			throw new IllegalStateException("Writing to the preferences failed with exception", e);
		}
	}
}
