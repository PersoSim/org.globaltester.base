package org.globaltester.core.ui.editors;

import java.lang.reflect.Constructor;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * Scanner that identifies XML relevant content types and returns either content
 * type tokens or text attribute tokens
 * 
 * @author amay
 * 
 */
public class XMLScanner extends GTRuleBasedPartitionScanner {
	public final static String CT_XML_PROC_INSTR = "__XML_PROC_INSTR";
	public final static String CT_XML_COMMENT = "__XML_COMMENT";
	public final static String CT_XML_TAG = "__XML_TAG";
	public final static String CT_XML_STRING_SINGLE_QUOTED = "__XML_STRING_SINGLE_QUOTED";
	public final static String CT_XML_STRING_DOUBLE_QUOTED = "__XML_STRING_DOUBLE_QUOTED";

	public static HashMap<String, EnumMap<TokenType, Object>> contentTypes = new HashMap<String, EnumMap<TokenType, Object>>();
	static {
		// add required data for content type XML_PROC_INSTR
		EnumMap<TokenType, Object> eMap = new EnumMap<TokenType, Object>(TokenType.class);
		eMap.put(TokenType.CONTENT_TYPE, XMLProcInstrRule.class);
		eMap.put(TokenType.TEXT_ATTRIBUTES, new TextAttribute(new Color(Display.getCurrent(),
				ColorConstants.XML_PROC_INSTR)));
		contentTypes.put(CT_XML_PROC_INSTR, eMap);

		// add required data for content type XML_COMMENT
		eMap = new EnumMap<TokenType, Object>(TokenType.class);
		eMap.put(TokenType.CONTENT_TYPE, XMLCommentRule.class);
		eMap.put(TokenType.TEXT_ATTRIBUTES, new TextAttribute(new Color(Display.getCurrent(),
				ColorConstants.XML_COMMENT)));
		contentTypes.put(CT_XML_COMMENT, eMap);

		// add required data for content type XML_TAG
		eMap = new EnumMap<TokenType, Object>(TokenType.class);
		eMap.put(TokenType.CONTENT_TYPE, XMLTagRule.class);
		eMap.put(TokenType.TEXT_ATTRIBUTES, new TextAttribute(new Color(Display.getCurrent(),
				ColorConstants.XML_TAG)));
		contentTypes.put(CT_XML_TAG, eMap);

		// add required data for content type XML_STRING_SINGLE_QUOTED
		eMap = new EnumMap<TokenType, Object>(TokenType.class);
		eMap.put(TokenType.CONTENT_TYPE, XMLStringSingleQuotedRule.class);
		eMap.put(TokenType.TEXT_ATTRIBUTES, new TextAttribute(new Color(Display.getCurrent(),
				ColorConstants.XML_STRING)));
		contentTypes.put(CT_XML_STRING_SINGLE_QUOTED, eMap);

		// add required data for content type XML_STRING_DOUBLE_QUOTED
		eMap = new EnumMap<TokenType, Object>(TokenType.class);
		eMap.put(TokenType.CONTENT_TYPE, XMLStringDoubleQuotedRule.class);
		eMap.put(TokenType.TEXT_ATTRIBUTES, new TextAttribute(new Color(Display.getCurrent(),
				ColorConstants.XML_STRING)));
		contentTypes.put(CT_XML_STRING_DOUBLE_QUOTED, eMap);
	}

	public XMLScanner(TokenType tokenType) {
		XMLScanner.addAllPredicateRules(this, tokenType);
	}

	private static IPredicateRule getRuleForContentType(String contentType,
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

	private static IToken getTokenForContentType(String contentType,
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

	public String[] getSupportedContentTypes() {
		return contentTypes.keySet().toArray(new String[0]);
	}

	/**
	 * Adds all XML related predicate rules to the given scanner
	 * 
	 * @param scanner scanner to add the rules to
	 * @param tokenType EnumType of GTRuleBasedPartitionScanner.TokenType that represents the type of token to be added
	 */
	public static void addAllPredicateRules(
			GTRuleBasedPartitionScanner scanner, TokenType tokenType) {
		for (Iterator<String> contentTypesIter = contentTypes.keySet()
				.iterator(); contentTypesIter.hasNext();) {
			String curContentType = contentTypesIter.next();
			scanner.addPredicateRule(getRuleForContentType(curContentType,
					tokenType));
		}
	}

}
