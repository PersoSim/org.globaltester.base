package org.globaltester.core.ui.editors;

import org.eclipse.jface.text.rules.*;

/**
 * SingleLineRule that matches single quoted strings
 * @author amay
 *
 */
public class XMLStringSingleQuotedRule extends SingleLineRule {
	
	public XMLStringSingleQuotedRule(String contentType) {
		this(ContentTypeAppearanceManager.getContentTypeToken(contentType));
	}
	
	public XMLStringSingleQuotedRule(IToken token) {
		super("'", "'", token, '\\');
	}

}
