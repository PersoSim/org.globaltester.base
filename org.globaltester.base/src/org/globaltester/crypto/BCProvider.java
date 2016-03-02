package org.globaltester.crypto;

import java.security.Provider;

import org.globaltester.cryptoprovider.Crypto;



/**
 * Class generates new Bouncy Castle Provider instance if not already done.
 * 
 * @author okaethler
 * 
 */
public class BCProvider {

	private BCProvider() {
	}
	
	public static Provider getProvider() {
		return Crypto.getCryptoProvider();
	}

}
