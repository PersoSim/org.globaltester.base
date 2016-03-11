package org.globaltester.base.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
//import org.globaltester.logging.logger.GtErrorLogger;
//import org.globaltester.logging.logger.TestLogger;
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
	 * @return generated Document or null if an error occurs (e.g. iFile does not exist, is not a valid xml file)
	 */
	public static Document readDocument(IFile iFile) {
		return readDocument(iFile, false); //TODO enable validation by default
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
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			serializer.output(doc, fos);
			
		} catch (IOException e) {
			e.printStackTrace();
			// TODO #833 Use proper logging mechanism
			//TestLogger.error(e);
			return;
		} finally {
			if (fos != null) {
				try {

					fos.close();

				} catch (IOException e) {
					e.printStackTrace();
					// TODO #833 Use proper logging mechanism
					//GtErrorLogger.log(Activator.PLUGIN_ID, e);

				}
			}
		}
	}

	public static void saveDoc(IFile iFile, Element root){
		saveDoc(iFile.getLocation().toFile(), root);
		try {
			iFile.refreshLocal(IFile.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (CoreException e) {
			// TODO #833 Use proper logging mechanism
			//GtErrorLogger.log(Activator.PLUGIN_ID, e);
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
