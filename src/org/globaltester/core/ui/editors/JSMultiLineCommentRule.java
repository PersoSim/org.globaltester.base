package org.globaltester.core.ui.editors;

import org.eclipse.jface.text.rules.*;

/**
 * MultiLineRule that matches JavaScript multi-line comments
 * 
 * @author amay
 * 
 */
public class JSMultiLineCommentRule extends MultiLineRule {

	public JSMultiLineCommentRule(String contentType) {
		this(ContentTypeAppearanceManager.getContentTypeToken(contentType));
	}
	
	public JSMultiLineCommentRule(IToken token) {
		super("/*", "*/", token);
	}

}
