package org.globaltester.core.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.globaltester.logging.logger.TestLogger;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.ProcessingInstruction;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

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
	 * @param validate
	 *            whather the input shall be validated by the parser or not
	 * @return generated Document
	 */
	public static Document readDocument(IFile iFile, boolean validate) {
		Document doc = null;

		File file = iFile.getLocation().toFile();
		if (!file.exists() || !file.isFile()) {
			// TODO handle this problem
			return null;
		}

		try {
			SAXBuilder b = new SAXBuilder(validate);
			doc = b.build(file);
		} catch (JDOMException jdomEx) {
			// TODO handle this exception
		} catch (IOException e) {
			// TODO handle this exception
		}

		return doc;

	}

	/**
	 * Writes the xml structure to file
	 * 
	 * @param iFile
	 *            File to which the document should be written
	 * @param doc
	 *            Document which should be written to the file
	 */
	public static void saveDoc(IFile iFile, Document doc) {

		// prepare output serializer
		Format format = Format.getRawFormat();
		format.setEncoding("ISO-8859-1");
		XMLOutputter serializer = new XMLOutputter(format);
		serializer.setFormat(Format.getPrettyFormat());

		// write the output to file
		try {
			FileOutputStream fos = new FileOutputStream(iFile.getLocation().toFile());
			serializer.output(doc, fos);
			fos.close();
		} catch (IOException e) {
			TestLogger.error(e);
			return;
		}

	}

	public static void saveDoc(IFile iFile, Element root) {
		Document doc = new Document(root);
		saveDoc(iFile, doc);
	}

	public static void saveDoc(IFile iFile, Element root, DocType docType) {
		Document doc = new Document(root, docType);
		saveDoc(iFile, doc);
	}
	
	@SuppressWarnings("unchecked")
	public static void saveDoc(IFile iFile, Element root, DocType docType, ProcessingInstruction pi) {
		Document doc = new Document(root, docType);
		doc.getContent().add(0, pi);
		
		saveDoc(iFile, doc);
	}
	
	

}
