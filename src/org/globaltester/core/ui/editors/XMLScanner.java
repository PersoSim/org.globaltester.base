package org.globaltester.core.ui.editors;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * Scanner that identifies XML relevant content types and returns either content
 * type tokens or text attribute tokens
 * 
 * @author amay
 * 
 */
public class XMLScanner extends GtScanner {
	
	public final static String CT_XML_PROC_INSTR = "__XML_PROC_INSTR";
	public final static String CT_XML_COMMENT = "__XML_COMMENT";
	public final static String CT_XML_TAG = "__XML_TAG";
	public final static String CT_XML_STRING_SINGLE_QUOTED = "__XML_STRING_SINGLE_QUOTED";
	public final static String CT_XML_STRING_DOUBLE_QUOTED = "__XML_STRING_DOUBLE_QUOTED";
	
	protected static HashMap<String, EnumMap<TokenType, Object>> contentTypes = new HashMap<String, EnumMap<TokenType, Object>>();

	static {
		// add required data for content type XML_PROC_INSTR
		EnumMap<TokenType, Object> eMap = new EnumMap<TokenType, Object>(
				TokenType.class);
		eMap.put(TokenType.CONTENT_TYPE, XMLProcInstrRule.class);
		eMap.put(TokenType.TEXT_ATTRIBUTES,
				new TextAttribute(new Color(Display.getCurrent(),
						ColorConstants.XML_PROC_INSTR)));
		contentTypes.put(CT_XML_PROC_INSTR, eMap);

		// add required data for content type XML_COMMENT
		eMap = new EnumMap<TokenType, Object>(TokenType.class);
		eMap.put(TokenType.CONTENT_TYPE, XMLCommentRule.class);
		eMap.put(TokenType.TEXT_ATTRIBUTES,
				new TextAttribute(new Color(Display.getCurrent(),
						ColorConstants.XML_COMMENT)));
		contentTypes.put(CT_XML_COMMENT, eMap);

		// add required data for content type XML_TAG
		eMap = new EnumMap<TokenType, Object>(TokenType.class);
		eMap.put(TokenType.CONTENT_TYPE, XMLTagRule.class);
		eMap.put(TokenType.TEXT_ATTRIBUTES,
				new TextAttribute(new Color(Display.getCurrent(),
						ColorConstants.XML_TAG)));
		contentTypes.put(CT_XML_TAG, eMap);

		// add required data for content type XML_STRING_SINGLE_QUOTED
		eMap = new EnumMap<TokenType, Object>(TokenType.class);
		eMap.put(TokenType.CONTENT_TYPE, XMLStringSingleQuotedRule.class);
		eMap.put(TokenType.TEXT_ATTRIBUTES,
				new TextAttribute(new Color(Display.getCurrent(),
						ColorConstants.XML_STRING)));
		contentTypes.put(CT_XML_STRING_SINGLE_QUOTED, eMap);

		// add required data for content type XML_STRING_DOUBLE_QUOTED
		eMap = new EnumMap<TokenType, Object>(TokenType.class);
		eMap.put(TokenType.CONTENT_TYPE, XMLStringDoubleQuotedRule.class);
		eMap.put(TokenType.TEXT_ATTRIBUTES,
				new TextAttribute(new Color(Display.getCurrent(),
						ColorConstants.XML_STRING)));
		contentTypes.put(CT_XML_STRING_DOUBLE_QUOTED, eMap);
	}

	public XMLScanner(TokenType tokenType) {
		super(tokenType);
		XMLScanner.addAllPredicateRules(this, tokenType);
	}

	public String[] getSupportedContentTypes() {
		return contentTypes.keySet().toArray(new String[0]);
	}

	/**
	 * Adds all XML related predicate rules to the given scanner
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
	 * Adds all XML related predicate rules that define document partitions to
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
		partitionContentTypes.add(CT_XML_PROC_INSTR);
		partitionContentTypes.add(CT_XML_COMMENT);
		partitionContentTypes.add(CT_XML_TAG);
		
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
