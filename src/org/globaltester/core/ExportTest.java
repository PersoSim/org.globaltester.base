package org.globaltester.core;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.globaltester.core.resources.GtResourceHelper;
import org.globaltester.document.export.Exporter;
import org.globaltester.junit.JUnitHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ExportTest {

	private File tempDir;
	private InputStream stylesheet;
	private InputStream sourcesZip;
	private InputStream testSpec;
	private File target;
	private File tempTestSpec;
	
	@Before
	public void createFolders() throws IOException{
		tempDir = File.createTempFile("hjp", "");

		// create a directory instead of the file, java 7 can create temporary directories
		// directly
		tempDir.delete();
		tempDir.mkdir();
		
		String folder = "files" + File.separator + "SuccessfullExportFiles" + File.separator;

		String plugin = "org.globaltester.core.test";
		stylesheet = FileLocator.openStream(
				Platform.getBundle(plugin), new Path("/" + folder
						+ "stylesheet.xsl"), false);
		sourcesZip = FileLocator.openStream(
				Platform.getBundle(plugin), new Path("/" + folder
						+ "sources.zip"), false);
		testSpec = FileLocator.openStream(
				Platform.getBundle(plugin), new Path("/" + folder
						+ "testSpecification.xml"), false);

		tempTestSpec = new File(tempDir, "testSpecification.xml");
		GtResourceHelper.copyFile(testSpec, tempTestSpec);
		target = new File(tempDir, "target.odt");
	}
	
	@Test
	public void testExportedFileHasContentXmlNullParams() throws IOException, CoreException {

		Exporter.export(target, tempTestSpec, stylesheet, sourcesZip, null);
		
		checkZipfile();
	}
	
	@Test
	public void testExportedFileHasContentXmlZeroParams() throws IOException, CoreException {
	
		HashMap<String,Object> params = new HashMap<String, Object>();
		Exporter.export(target, tempTestSpec, stylesheet, sourcesZip, params);
		
		checkZipfile();
	}
	
	@Test
	public void testExportedFileHasContentXmlParams() throws IOException, CoreException {
	
		HashMap<String,Object> params = new HashMap<String, Object>();
		params.put("test", new Object());
		Exporter.export(target, tempTestSpec, stylesheet, sourcesZip, params);
		
		checkZipfile();
	}
	
	private void checkZipfile() throws IOException{
		ZipFile result = new ZipFile(target);
		ZipEntry entry = result.getEntry("content.xml");
		
		// copy entry to tempfile, because size is not necessarily known in the zip entry
		File content = new File(tempDir, "content.xml");
		GtResourceHelper.copyFile(result.getInputStream(entry), content);
		result.close();
		
		assertTrue("zip entry does not exist", entry != null);
		assertTrue("content.xml is empty", content.length() > 0);
	}
	
	@After
	public void cleanup(){
		JUnitHelper.recursiveDelete(tempDir);
	}
}
