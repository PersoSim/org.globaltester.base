package org.globaltester.core.ui.editors;

import org.eclipse.jface.text.rules.*;

/**
 * SingleLineRule that matches double quoted strings
 * @author amay
 *
 */
public class XMLStringDoubleQuotedRule extends SingleLineRule {
	
	public XMLStringDoubleQuotedRule(String contentType) {
		this(ContentTypeAppearanceManager.getContentTypeToken(contentType));
	}
	
	public XMLStringDoubleQuotedRule(IToken token) {
		super("\"", "\"", token, '\\');
	}

}
