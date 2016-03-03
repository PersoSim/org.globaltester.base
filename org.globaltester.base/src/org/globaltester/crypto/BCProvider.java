package org.globaltester.crypto;

import java.security.Provider;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.globaltester.cryptoprovider.Crypto;
import org.globaltester.cryptoprovider.Cryptoprovider;
import org.osgi.framework.Constants;



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
		String filterString = "(&(" + Constants.OBJECTCLASS + "=" + Cryptoprovider.class.getName() + ")" +
		"(" + Cryptoprovider.NAME + "=" + BouncyCastleProvider.PROVIDER_NAME + ")" +
		"(" + Cryptoprovider.VERSION + "=1.46))";
		
		return Crypto.getCryptoProvider(filterString);
	}

}
