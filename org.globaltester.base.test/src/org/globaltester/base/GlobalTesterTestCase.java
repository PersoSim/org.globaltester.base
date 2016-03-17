package org.globaltester.base;
import org.globaltester.cryptoprovider.Crypto;
import org.globaltester.cryptoprovider.bc.ProviderBc;
import org.junit.BeforeClass;

/**
 * Superclass for all test cases for the GlobalTester.
 * 
 * @author mboonk
 * 
 */
public class GlobalTesterTestCase {

	@BeforeClass
	public static void setupClass(){
		Crypto.setCryptoProvider(new ProviderBc().getCryptoProviderObject());
	}

}
