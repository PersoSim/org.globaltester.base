package org.globaltester.core.ui.editors;

import org.eclipse.jface.text.rules.*;

/**
 * MultiLineRule that matches XML comments
 * @author amay
 *
 */
public class XMLCommentRule extends MultiLineRule {
	
	public XMLCommentRule(String contentType) {
		this(ContentTypeAppearanceManager.getContentTypeToken(contentType));
	}
	
	public XMLCommentRule(IToken token) {
		super("<!--", "-->", token);
	}

}
