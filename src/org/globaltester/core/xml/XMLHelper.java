package org.globaltester.core.xml;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class XMLHelper {

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

}
