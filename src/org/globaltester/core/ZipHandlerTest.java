package org.globaltester.core;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.globaltester.document.export.ZipHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;

public class ZipHandlerTest {

	File tempFile;
	
	@Before
	public void prepare() throws IOException{
		 tempFile = File.createTempFile("zipHandlerTestFile", "zip");
	}
	
	@Test
	public void testWithZipFile() throws IOException{
		FileOutputStream out = new FileOutputStream(tempFile);
		Bundle curBundle = Platform.getBundle("org.globaltester.core.test");
		URL url = FileLocator.find(curBundle, new Path("files/testArchive.zip"), null);
		IPath pluginDir = new Path(FileLocator.toFileURL(url).getPath());
		
		File source = pluginDir.toFile();
		ZipInputStream input = new ZipInputStream(new FileInputStream(source));
		ZipOutputStream zipOut = ZipHandler.append(input, out);
		input.close();
		zipOut.close();
		out.close();

		ZipFile zip = new ZipFile(tempFile);
		assertTrue("Zip should contain testFile", !zip.getEntry("testFile").isDirectory());
		assertTrue("Zip should contain testFolder", zip.getEntry("testFolder/").isDirectory());
		assertTrue("Zip should contain testFolder/testFile", !zip.getEntry("testFolder/testFile").isDirectory());
	}
	
	@After
	public void closeStreams() throws IOException{
		tempFile.delete();
	}
}
