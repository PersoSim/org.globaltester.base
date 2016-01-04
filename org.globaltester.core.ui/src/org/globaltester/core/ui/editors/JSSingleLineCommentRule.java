package org.globaltester.core.ui.editors;

import org.eclipse.jface.text.rules.*;
import org.globaltester.core.ui.editors.GtScanner.TokenType;

/**
 * EndOfLineRule that matches JavaScript single line comments
 * @author amay
 *
 */
public class JSSingleLineCommentRule extends EndOfLineRule {
	
	public JSSingleLineCommentRule(String contentType) {
		this(JSScanner.getTokenForContentType(contentType, TokenType.CONTENT_TYPE));
	}
	
	public JSSingleLineCommentRule(IToken token) {
		super("//", token);
	}

}
