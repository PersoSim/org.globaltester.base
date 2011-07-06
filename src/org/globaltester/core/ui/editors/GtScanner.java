package org.globaltester.core.ui.editors;

import java.lang.reflect.Constructor;
import java.util.EnumMap;
import java.util.HashMap;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class GtScanner extends RuleBasedPartitionScanner {
	
	public enum TokenType {
		CONTENT_TYPE,
		TEXT_ATTRIBUTES
	}
	
	public static final String CT_DEFAULT = "__DEFAULT";

	protected static HashMap<String, EnumMap<TokenType, Object>> contentTypes = new HashMap<String, EnumMap<TokenType, Object>>();
	
	protected static IPredicateRule getRuleForContentType(String contentType,
			TokenType tokenType) {
		Object ruleInstance = null;
		if (contentTypes.containsKey(contentType)) {
			IToken token = getTokenForContentType(contentType, tokenType);
			Class<?> paramTypes[] = new Class<?>[1];
			paramTypes[0] = IToken.class;

			Class<?> ruleClass = (Class<?>) contentTypes.get(contentType).get(TokenType.CONTENT_TYPE);
			try {
				Constructor<?> c = ruleClass.getConstructor(IToken.class);
				ruleInstance = c.newInstance(new Object[] { token });
			} catch (Exception e) {
				// ignore rule if it can not be instantiated
			}
		}

		if ((ruleInstance != null) && (ruleInstance instanceof IPredicateRule)) {
			return (IPredicateRule) ruleInstance;
		} else {
			return null;
		}

	}

	protected static IToken getTokenForContentType(String contentType,
			TokenType tokenType) {
		switch (tokenType) {
		case CONTENT_TYPE:
			return new Token(contentType);
		case TEXT_ATTRIBUTES:
			return new Token(contentTypes.get(contentType).get(TokenType.TEXT_ATTRIBUTES));

		default:
			return null;
		}

	}
	
	public GtScanner(TokenType tokenType) {
		IToken defaultToken = null;
		switch (tokenType) {
		case CONTENT_TYPE:
			defaultToken =  new Token(CT_DEFAULT);
		case TEXT_ATTRIBUTES:
			defaultToken = new Token(new TextAttribute(new Color(Display.getCurrent(),
					ColorConstants.DEFAULT)));
		}
		this.setDefaultReturnToken(defaultToken);
	}

	protected HashMap<String, IPredicateRule> rules = new HashMap<String, IPredicateRule>();

	public String[] getLegalContentTypes() {
		return rules.keySet().toArray(new String[]{});
	}

	public void addPredicateRule(String contentType, IPredicateRule ruleForContentType) {
		rules.put(contentType, ruleForContentType);
		setPredicateRules(rules.values().toArray(new IPredicateRule[]{}));
	}

}
