package org.globaltester.base.resources;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.globaltester.base.resources.GtResourceHelper;
import org.globaltester.junit.JUnitHelper;
import org.globaltester.junit.TestNature;
import org.junit.After;
import org.junit.Test;

public class GtResourceHelperTest {
	static final String testProject = "testProject";
	static final String testFolder = "testFolder";
	static final String testFile = "testFile";
	
	
	@Test
	public void testCreateWithAllParents() throws CoreException{
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(testProject);
		IFolder folder = project.getFolder(testFolder);
		IFile file = folder.getFile(testFile);
		GtResourceHelper.createWithAllParents(file);
		assertTrue(project.exists());
		assertTrue(folder.exists());
		assertTrue(file.exists());
	}
	
	@Test
	public void testCopyPluginFilesToWorkspaceProject() throws IOException, CoreException{
		IProject project = JUnitHelper.createEmptyProject(testProject);
		GtResourceHelper.copyPluginFilesToWorkspaceProject("org.globaltester.core.test", project, "files", testFile, testFolder);
		assertTrue(project.getFolder(testFolder).exists());
		assertTrue(project.getFolder(testFolder).getFile(testFile).exists());
		assertTrue(project.getFile(testFile).exists());
	}
	
	@Test
	public void testCopyStream() throws IOException{
		File src = File.createTempFile(testFile, "");
		File dest = File.createTempFile(testFile, "");
		
		FileOutputStream os = null;
		byte[] fileContent;
		
		try {
			os = new FileOutputStream(src);
			fileContent = new byte [] {0,1,2,3,4,5,6,7,8};
		os.write(fileContent );
		} finally {
			if(os != null) {
		os.close();
			}
		}
		
		FileInputStream is = null;
		
		try {
			is = new FileInputStream(src);		
		os = new FileOutputStream(dest);
		
		GtResourceHelper.copyStream(is, os);
		} finally {
			if(is != null) {
		is.close();
			}
			
			if(is != null) {
		os.close();
			}
		}
		
		byte [] read;
		int length;
		
		try {
		is = new FileInputStream(dest);
			read = new byte [fileContent.length];
			length = is.read(read);
		} finally {
			if(is != null) {
				is.close();
			}
		}
		
		assertEquals(fileContent.length, length);
		assertArrayEquals(fileContent, read);
	}
	
	@Test
	public void testCopyFile() throws IOException{
		byte [] content = new byte []{0,1,2,3,4,5,6,7,8};
		File source = JUnitHelper.createTemporaryFile(content);
		File destination = File.createTempFile("testdest", "");
		GtResourceHelper.copyFile(new FileInputStream(source), destination);
		
		FileInputStream in = null;
		byte [] result;
		
		try {
			in = new FileInputStream(destination);
			result = new byte [content.length];
		in.read(result);
		} finally {
			if(in != null) {
				in.close();
			}
		}
		
		assertArrayEquals(content, result);
	}
	
	@Test
	public void testCopyFiles() throws IOException{
		File sourceFolder = JUnitHelper.createTemporaryFolder();
		File destinationFolder = JUnitHelper.createTemporaryFolder();
		File subFolder = new File(sourceFolder, "sub");
		subFolder.mkdir();
		File subFile = new File(sourceFolder, "subFile");
		subFile.createNewFile();
		File subSubFile = new File(subFolder, "subSubFile");
		subSubFile.createNewFile();
		
		GtResourceHelper.copyFiles(sourceFolder, destinationFolder);

		assertTrue(JUnitHelper.compareFiles(subFile, new File (destinationFolder, subFile.getName()), true));
		File destSubFolder = new File (destinationFolder, subFolder.getName());
		assertTrue(JUnitHelper.compareFiles(subFolder, destSubFolder, true));
		assertTrue(JUnitHelper.compareFiles(subSubFile, new File (destSubFolder, subSubFile.getName()), true));
	}
	
	@Test
	public void testCreateFolder() throws CoreException, IOException{
		IProject project = JUnitHelper.createDefaultCardConfig();
		IFolder folder = project.getFolder("test");
		assertFalse(folder.exists());
		GtResourceHelper.createFolder(folder);
		assertTrue(folder.exists());
	}
	
	@Test
	public void testAddToProjectStructure() throws IOException, CoreException{
		IProject project = JUnitHelper.createEmptyProject(testProject);
		String folder = "folder";
		String subFolder = "subFolder";
		String [] paths = new String [] {folder, folder + File.separator + subFolder};

		assertFalse(project.getFolder(folder).exists());
		assertFalse(project.getFolder(folder).getFolder(subFolder).exists());
		GtResourceHelper.addToProjectStructure(project, paths);
		assertTrue(project.getFolder(folder).exists());
		assertTrue(project.getFolder(folder).getFolder(subFolder).exists());
	}
	
	@Test
	public void testAddNature() throws IOException, CoreException{
		IProject project = JUnitHelper.createEmptyProject(testProject);
		assertFalse(project.hasNature(TestNature.NATURE_ID));
		GtResourceHelper.addNature(project, TestNature.NATURE_ID);
		assertTrue(project.hasNature(TestNature.NATURE_ID));
	}
	
	@Test
	public void testCreateEmptyProject(){
		IProject project = GtResourceHelper.createEmptyProject("testProject", ResourcesPlugin.getWorkspace().getRoot().getLocationURI());
		assertTrue(project.exists());
	}
	
	@Test
	public void testGetProjectNamesWithNature() throws IOException, CoreException{
		IProject test1 = JUnitHelper.createEmptyProject("test1");
		GtResourceHelper.addNature(test1, TestNature.NATURE_ID);
		IProject test2 = JUnitHelper.createEmptyProject("test2");
		Set<String> projectNames = GtResourceHelper.getProjectNamesWithNature(TestNature.NATURE_ID);
		assertTrue(projectNames.size() == 1);
		assertTrue(projectNames.contains(test1.getName()));
		assertFalse(projectNames.contains(test2.getName()));
	}
	
	@Test
	public void testGetIFileForLocation() throws CoreException{
		IProject project = JUnitHelper.createEmptyProject(testProject);
		InputStream in = new InputStream() {
			
			@Override
			public int read() throws IOException {
				return -1;
			}
		};
		IFile test = project.getFile(testFile);
		test.create(in, false, null);
		IFile result = GtResourceHelper.getIFileForLocation(project.getLocation() + "/" + testFile);
		assertTrue(result.equals(test));
	}
	
	@After
	public void cleanUp() throws CoreException{
		JUnitHelper.emptyWorkspace();
	}
}
