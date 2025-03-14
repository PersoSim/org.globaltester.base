package org.globaltester.crypto;

import java.security.Provider;

import org.globaltester.cryptoprovider.Crypto;
import org.globaltester.cryptoprovider.Cryptoprovider;
import org.osgi.framework.Constants;

public class BCProvider {

	private BCProvider() {
	}

	public static Provider getProvider() {
		String filterString = "(&(" + Constants.OBJECTCLASS + "=" + Cryptoprovider.class.getName() + ")" +
		"(" + Cryptoprovider.NAME + "=" + "BC" + ")" +
		"(" + Cryptoprovider.VERSION + "=2.7307))";

		return Crypto.getCryptoProvider(filterString);
	}

}
