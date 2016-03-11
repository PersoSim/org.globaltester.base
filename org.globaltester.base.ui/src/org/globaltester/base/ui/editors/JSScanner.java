package org.globaltester.base.ui.editors;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * Scanner that identifies JavaScript relevant content types and returns either
 * content type tokens or text attribute tokens
 * 
 * @author amay
 * 
 */
public class JSScanner extends GtScanner {

	public final static String CT_JS_KEYWORD = "__JS_KEYWORD";
	public final static String CT_JS_MULTILINE_COMMENT = "__JS_MULTILINE_COMMENT";
	public final static String CT_JS_SINGLELINE_COMMENT = "__JS_SINGLELINE_COMMENT";
	
	protected static HashMap<String, EnumMap<TokenType, Object>> contentTypes = new HashMap<String, EnumMap<TokenType, Object>>();

	// init supported content types
	static {
		// add required data for content type JS_KEYWORD
		EnumMap<TokenType, Object> eMap = new EnumMap<TokenType, Object>(
				TokenType.class);
		eMap.put(TokenType.CONTENT_TYPE, JsKeyWordRule.class);
		eMap.put(TokenType.TEXT_ATTRIBUTES,
				new TextAttribute(new Color(Display.getCurrent(),
						ColorConstants.JS_KEYWORD), null, SWT.BOLD));
		contentTypes.put(CT_JS_KEYWORD, eMap);

		// add required data for content type JS_MULTILINE_COMMENT
		eMap = new EnumMap<TokenType, Object>(TokenType.class);
		eMap.put(TokenType.CONTENT_TYPE, JSMultiLineCommentRule.class);
		eMap.put(TokenType.TEXT_ATTRIBUTES,
				new TextAttribute(new Color(Display.getCurrent(),
						ColorConstants.JS_COMMENT)));
		contentTypes.put(CT_JS_MULTILINE_COMMENT, eMap);

		// add required data for content type JS_SINGLELINE_COMMENT
		eMap = new EnumMap<TokenType, Object>(TokenType.class);
		eMap.put(TokenType.CONTENT_TYPE, JSSingleLineCommentRule.class);
		eMap.put(TokenType.TEXT_ATTRIBUTES,
				new TextAttribute(new Color(Display.getCurrent(),
						ColorConstants.JS_COMMENT)));
		contentTypes.put(CT_JS_SINGLELINE_COMMENT, eMap);

	}

	public JSScanner(TokenType tokenType) {
		super(tokenType);
		JSScanner.addAllPredicateRules(this, tokenType);
	}

	public String[] getSupportedContentTypes() {
		return contentTypes.keySet().toArray(new String[0]);
	}

	/**
	 * Adds all JS related predicate rules to the given scanner
	 * 
	 * @param scanner
	 *            scanner to add the rules to
	 * @param tokenType
	 *            EnumType of GTRuleBasedPartitionScanner.TokenType that
	 *            represents the type of token to be added
	 */
	public static void addAllPredicateRules(GtScanner scanner,
			TokenType tokenType) {
		for (Iterator<String> contentTypesIter = contentTypes.keySet()
				.iterator(); contentTypesIter.hasNext();) {
			String curContentType = contentTypesIter.next();
			IPredicateRule curRule = getRuleForContentType(curContentType,
					tokenType, contentTypes);
			if (curRule != null) {
				scanner.addPredicateRule(curContentType, curRule);
			}
		}
	}

	/**
	 * Adds all JSs related predicate rules that define document partitions to
	 * the given scanner
	 * 
	 * @param scanner
	 *            scanner to add the rules to
	 * @param tokenType
	 *            EnumType of GTRuleBasedPartitionScanner.TokenType that
	 *            represents the type of token to be added
	 */
	public static void addAllPartitionRules(GtScanner scanner,
			TokenType tokenType) {
		ArrayList<String> partitionContentTypes = new ArrayList<String>();
		partitionContentTypes.add(CT_JS_MULTILINE_COMMENT);
		
		for (Iterator<String> contentTypesIter = partitionContentTypes
				.iterator(); contentTypesIter.hasNext();) {
			String curContentType = contentTypesIter.next();
			IPredicateRule curRule = getRuleForContentType(curContentType,
					tokenType, contentTypes);
			if (curRule != null) {
				scanner.addPredicateRule(curContentType, curRule);
			}
		}
	}

	public static IToken getTokenForContentType(String contentType,
			TokenType tokenType) {
		return getTokenForContentType(contentType, tokenType, contentTypes);
	}

}
