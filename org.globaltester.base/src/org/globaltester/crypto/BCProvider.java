package org.globaltester.crypto;

import java.security.Provider;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * Class generates new Bouncy Castle Provider instance if not already done.
 * 
 * @author okaethler
 * 
 */
public class BCProvider {

	private BCProvider() {
	}

	private static Provider bcProv = null;
	
	public static Provider getProvider() {
		if (bcProv == null) {
			bcProv = new BouncyCastleProvider();

			Security.addProvider(bcProv);
		}
		return bcProv;
	}

}
