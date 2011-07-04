package org.globaltester.core.ui.editors;

import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;

/**
 * MultiLineRule that matches XML Processing Instuctions
 * @author amay
 *
 */
public class XMLProcInstrRule extends MultiLineRule {
	

	public XMLProcInstrRule(String contentType) {
		this(ContentTypeAppearanceManager.getContentTypeToken(contentType));
	}
	
	public XMLProcInstrRule(IToken token) {
		super("<?", "?>", token);
	}

}
