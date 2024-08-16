package org.globaltester.base.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.globaltester.logging.BasicLogger;
//import org.globaltester.logging.logger.GtErrorLogger;
//import org.globaltester.logging.logger.TestLogger;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.ProcessingInstruction;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * This class provides some convenience functions for handling XMl files within
 * IResources
 * 
 * @author amay
 * 
 */
public class XMLHelper {

	/**
	 * Build an XML document from a given IFile.
	 * 
	 * @param iFile
	 *            Resource that contains the XML file
	 * @return generated Document or null if an error occurs (e.g. iFile does not exist, is not a valid xml file)
	 */
	public static Document readDocument(IFile iFile) {
		return readDocument(iFile, false); //IMPL enable validation by default
	}
	
	/**
	 * Build an XML document from a given IFile.
	 * 
	 * @param iFile
	 *            Resource that contains the XML file
	 * @param validate
	 *            whather the input shall be validated by the parser or not
	 * @return generated Document or null if an error occurs (e.g. iFile does not exist, is not a file)
	 */
	public static Document readDocument(IFile iFile, boolean validate) {
		Document doc = null;

		File file = iFile.getLocation().toFile();
		if (!file.exists() || !file.isFile()) {
			return null;
		}

		try {
			SAXBuilder b = new SAXBuilder(validate);
			doc = b.build(file);
		} catch (JDOMException | IOException e) {
			// return null document
			BasicLogger.logException(XMLHelper.class, e);
		}

		return doc;

	}

	/**
	 * Writes the xml structure to file
	 * 
	 * @param file
	 *            File to which the document should be written
	 * @param doc
	 *            Document which should be written to the file
	 */
	public static void saveDoc(File file, Document doc) {

		// prepare output serializer
		Format format = Format.getRawFormat();
		format.setEncoding("ISO-8859-1");
		XMLOutputter serializer = new XMLOutputter(format);
		serializer.setFormat(Format.getPrettyFormat());

		// write the output to file
		try (FileOutputStream fos = new FileOutputStream(file)){
			
			serializer.output(doc, fos);
			
		} catch (IOException e) {
			BasicLogger.logException(XMLHelper.class, e);
			return;
		}
	}

	public static void saveDoc(IFile iFile, Element root){
		saveDoc(iFile.getLocation().toFile(), root);
		try {
			iFile.refreshLocal(IFile.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (CoreException e) {
			BasicLogger.logException(XMLHelper.class, e);
		}
	}

	public static void saveDoc(File file, Element root) {
		Document doc = new Document(root);
		saveDoc(file, doc);
	}

	public static void saveDoc(IFile iFile, Element root, DocType docType) {
		Document doc = new Document(root, docType);
		saveDoc(iFile.getLocation().toFile(), doc);
	}
	
	@SuppressWarnings("unchecked")
	public static void saveDoc(IFile iFile, Element root, DocType docType, ProcessingInstruction pi) {
		Document doc = new Document(root, docType);
		doc.getContent().add(0, pi);
		
		saveDoc(iFile.getLocation().toFile(), doc);
	}
	
	

}
