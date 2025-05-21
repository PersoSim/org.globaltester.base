package org.globaltester.base.xml;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.globaltester.base.resources.GtResourceHelper;
import org.globaltester.junit.JUnitHelper;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;
import org.junit.After;
import org.junit.Test;

public class XmlHelperTest {
	@Test
	public void testReadDocument() throws IOException, CoreException{
		IProject project =JUnitHelper.createEmptyProject("testProject");
		String file = "testSpecification.gtspec";
		GtResourceHelper.copyPluginFilesToWorkspaceProject("org.globaltester.base.test", project, "files/SuccessfulExportFiles", file);
		Document doc = XMLHelper.readDocument(project.getFile(file));
		assertTrue(doc.getRootElement().getName().equals("TestSpecification"));
	}
	
	@Test
	public void testSaveDoc() throws CoreException, JDOMException, IOException{
		IProject project =JUnitHelper.createEmptyProject("testProject");
		String dest = "destination.xml";
		IFile destination = project.getFile(dest);
		XMLHelper.saveDoc(destination, new Element("root"));
		assertTrue(destination.exists());
		SAXBuilder b = new SAXBuilder(XMLReaders.NONVALIDATING);
		Document result = b.build(destination.getLocation().toFile());
		assertTrue(result.getRootElement().getName().equals("root"));
	}
	
	@After
	public void cleanUp() throws CoreException{
		JUnitHelper.emptyWorkspace();
	}
}
