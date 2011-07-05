package org.globaltester.core.ui.editors;

import org.eclipse.jface.text.rules.*;
import org.globaltester.core.ui.editors.GtScanner.TokenType;

/**
 * MultiLineRule that matches JavaScript multi-line comments
 * 
 * @author amay
 * 
 */
public class JSMultiLineCommentRule extends MultiLineRule {

	public JSMultiLineCommentRule(String contentType) {
		this(JSScanner.getTokenForContentType(contentType, TokenType.CONTENT_TYPE));
	}
	
	public JSMultiLineCommentRule(IToken token) {
		super("/*", "*/", token);
	}

}
