package org.globaltester.core.ui.editors;

import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.globaltester.core.ui.editors.GtScanner.TokenType;

/**
 * MultiLineRule that matches XML Processing Instuctions
 * @author amay
 *
 */
public class XMLProcInstrRule extends MultiLineRule {
	

	public XMLProcInstrRule(String contentType) {
		this(XMLScanner.getTokenForContentType(contentType, TokenType.CONTENT_TYPE));
	}
	
	public XMLProcInstrRule(IToken token) {
		super("<?", "?>", token);
	}

}
