package org.globaltester.core.ui.editors;

import org.eclipse.jface.text.rules.*;

/**
 * EndOfLineRule that matches JavaScript single line comments
 * @author amay
 *
 */
public class JSSingleLineCommentRule extends EndOfLineRule {
	
	public JSSingleLineCommentRule(String contentType) {
		this(ContentTypeAppearanceManager.getContentTypeToken(contentType));
	}
	
	public JSSingleLineCommentRule(IToken token) {
		super("//", token);
	}

}
