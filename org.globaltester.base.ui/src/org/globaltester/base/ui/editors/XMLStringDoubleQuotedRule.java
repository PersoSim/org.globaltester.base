package org.globaltester.base.ui.editors;

import org.eclipse.jface.text.rules.*;
import org.globaltester.base.ui.editors.GtScanner.TokenType;

/**
 * SingleLineRule that matches double quoted strings
 * @author amay
 *
 */
public class XMLStringDoubleQuotedRule extends SingleLineRule {
	
	public XMLStringDoubleQuotedRule(String contentType) {
		this(XMLScanner.getTokenForContentType(contentType, TokenType.CONTENT_TYPE));
	}
	
	public XMLStringDoubleQuotedRule(IToken token) {
		super("\"", "\"", token, '\\');
	}

}
