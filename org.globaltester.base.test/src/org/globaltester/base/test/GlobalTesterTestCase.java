package org.globaltester.base.test;
import java.security.Provider;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.BeforeClass;

import de.persosim.simulator.crypto.Crypto;

/**
 * Superclass for all test cases for the GlobalTester.
 * 
 * @author mboonk
 * 
 */
public class GlobalTesterTestCase {

	protected static Provider bcProvider;
	
	@BeforeClass
	public static void setupClass(){
		bcProvider = new BouncyCastleProvider();
		Security.addProvider(bcProvider);
		Crypto.setCryptoProvider(bcProvider);

	}

}
