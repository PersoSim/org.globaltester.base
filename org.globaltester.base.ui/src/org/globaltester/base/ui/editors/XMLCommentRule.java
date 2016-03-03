package org.globaltester.base.ui.editors;

import org.eclipse.jface.text.rules.*;
import org.globaltester.base.ui.editors.GtScanner.TokenType;

/**
 * MultiLineRule that matches XML comments
 * @author amay
 *
 */
public class XMLCommentRule extends MultiLineRule {
	
	public XMLCommentRule(String contentType) {
		this(XMLScanner.getTokenForContentType(contentType, TokenType.CONTENT_TYPE));
	}
	
	public XMLCommentRule(IToken token) {
		super("<!--", "-->", token);
	}

}
