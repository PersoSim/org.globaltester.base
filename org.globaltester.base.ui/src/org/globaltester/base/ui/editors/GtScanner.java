package org.globaltester.base.ui.editors;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.globaltester.base.ui.Activator;
import org.globaltester.logging.legacy.logger.GtErrorLogger;

public class GtScanner extends RuleBasedPartitionScanner {
	
	public enum TokenType {
		CONTENT_TYPE,
		TEXT_ATTRIBUTES
	}
	
	public static final String CT_DEFAULT = "__DEFAULT";

	protected static HashMap<String, EnumMap<TokenType, Object>> contentTypes = new HashMap<String, EnumMap<TokenType, Object>>();
	
	protected static IPredicateRule getRuleForContentType(String contentType,
			TokenType tokenType, HashMap<String, EnumMap<TokenType, Object>> contentTypes) {
		Object ruleInstance = null;
		if (contentTypes.containsKey(contentType)) {
			IToken token = getTokenForContentType(contentType, tokenType, contentTypes);
			Class<?>[] paramTypes = new Class<?>[1];
			paramTypes[0] = IToken.class;

			Class<?> ruleClass = (Class<?>) contentTypes.get(contentType).get(TokenType.CONTENT_TYPE);
			try {
				Constructor<?> c = ruleClass.getConstructor(IToken.class);
				ruleInstance = c.newInstance(new Object[] { token });
			} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
				// ignore rule if it can not be instantiated
				GtErrorLogger.log(Activator.PLUGIN_ID, "Unable to instantiate predicate rule for content type \""+ contentType + "\"", e);
			}
		}

		if (ruleInstance instanceof IPredicateRule) {
			return (IPredicateRule) ruleInstance;
		} else {
			return null;
		}

	}

	public static IToken getTokenForContentType(String contentType,
			TokenType tokenType, Map<String, EnumMap<TokenType, Object>> contentTypes) {
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
		if (tokenType ==  TokenType.TEXT_ATTRIBUTES) {
			fDefaultReturnToken = new Token(new TextAttribute(new Color(Display.getCurrent(),
					ColorConstants.DEFAULT)));
		} else {
			fDefaultReturnToken =  new Token(CT_DEFAULT);
		}
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
