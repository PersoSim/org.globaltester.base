package org.globaltester.core.ui.editors;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
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
	//init supported content types
	static {
		// add required data for default content type
//		EnumMap<TokenType, Object> eMap = new EnumMap<TokenType, Object>(TokenType.class);
//		eMap.put(TokenType.CONTENT_TYPE, null); //no rule required for default content type
//		eMap.put(TokenType.TEXT_ATTRIBUTES, new TextAttribute(new Color(Display.getCurrent(),
//				ColorConstants.DEFAULT)));
//		contentTypes.put(ContentTypeAppearanceManager.CONTENT_TYPE_DEFAULT, eMap);

	}
	
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

	protected ArrayList<IPredicateRule> rules = new ArrayList<IPredicateRule>();

	public String[] getLegalContentTypes() {
		IPredicateRule[] ruleArray = rules.toArray(new IPredicateRule[]{});
		String[] contentTypes = new String[ruleArray.length];
		
		for (int i = 0; i < ruleArray.length; i++) {
			Object o = null;
			
			try {
				o = ruleArray[i].getSuccessToken().getData();
			} catch (NullPointerException npe) {
				//ignore, this content type will be added as CT_DEFAULT
			}
			if (o instanceof String) {
				contentTypes[i] = (String) o;	
			} else {
				contentTypes[i] = null; //GtScanner.CT_DEFAULT;
			}
			
		}
		
		return contentTypes;
	}

	public void addPredicateRule(IPredicateRule ruleForContentType) {
		rules.add(ruleForContentType);
		setPredicateRules(rules.toArray(new IPredicateRule[]{}));
	}

}
