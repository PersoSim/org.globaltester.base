package org.globaltester.crypto;

import java.security.Provider;

import org.globaltester.cryptoprovider.Crypto;
import org.globaltester.cryptoprovider.Cryptoprovider;
import org.globaltester.logging.BasicLogger;
import org.globaltester.logging.tags.LogLevel;
import org.osgi.framework.Constants;

public class BCProvider {

	public static final String BC_VERSION = "2.7307";

	public static final String BC_FILTER = "(&(" + Constants.OBJECTCLASS + "=" + Cryptoprovider.class.getName() + ")"
			+ "(" + Cryptoprovider.NAME + "=" + "BC" + ")" + "(" + Cryptoprovider.VERSION + "=" + BC_VERSION + "))";

	static {
		BasicLogger.log(BCProvider.class, "Get CryptoProvider with filter: '" + BC_FILTER + "'", LogLevel.INFO);
	}

	private BCProvider() {
		// hide implicit public constructor
	}

	public static Provider getProvider() {
		return Crypto.getCryptoProvider(BC_FILTER);
	}

}
