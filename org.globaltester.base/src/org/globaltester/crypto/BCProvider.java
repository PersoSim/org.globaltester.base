package org.globaltester.crypto;

import java.security.Provider;

import org.globaltester.cryptoprovider.Crypto;
import org.globaltester.cryptoprovider.Cryptoprovider;
import org.osgi.framework.Constants;

/**
 * This class provides legacy support for all code relying on its
 * {@link #getProvider()} method to provide a BC 1.46 {@link Cryptoprovider} for
 * compatibility issues while most other code already relies on generic JCE API
 * and also works with newer/latest BC versions or any {@link Provider} at all
 * capable of providing the required cryptographic primitives.
 * 
 * @author slutters
 * 
 */
public class BCProvider {

	private BCProvider() {
	}
	
	public static Provider getProvider() {
		String filterString = "(&(" + Constants.OBJECTCLASS + "=" + Cryptoprovider.class.getName() + ")" +
		"(" + Cryptoprovider.NAME + "=" + "BC" + ")" +
		"(" + Cryptoprovider.VERSION + "=1.7801))";
		
		return Crypto.getCryptoProvider(filterString);
	}

}
