package org.globaltester.base.ui.editors;

import org.eclipse.jface.text.rules.*;
import org.globaltester.base.ui.editors.GtScanner.TokenType;

/**
 * SingleLineRule that matches single quoted strings
 * @author amay
 *
 */
public class XMLStringSingleQuotedRule extends SingleLineRule {
	
	public XMLStringSingleQuotedRule(String contentType) {
		this(XMLScanner.getTokenForContentType(contentType, TokenType.CONTENT_TYPE));
	}
	
	public XMLStringSingleQuotedRule(IToken token) {
		super("'", "'", token, '\\');
	}

}
