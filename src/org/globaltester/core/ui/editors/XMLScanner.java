package org.globaltester.core.ui.editors;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
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
public class XMLScanner extends RuleBasedPartitionScanner {
	public final static String CT_XML_PROC_INSTR = "__XML_PROC_INSTR";
	public final static String CT_XML_COMMENT = "__XML_COMMENT";
	public final static String CT_XML_TAG = "__XML_TAG";
	public final static String CT_XML_STRING_SINGLE_QUOTED = "__XML_STRING_SINGLE_QUOTED";
	public final static String CT_XML_STRING_DOUBLE_QUOTED = "__XML_STRING_DOUBLE_QUOTED";

	public static HashMap<String, Object[]> contentTypes = new HashMap<String, Object[]>();
	{
		//add required data for content type XML_PROC_INSTR
		Object oArr[] = new Object[TokenType.values().length];
		oArr[0] = XMLProcInstrRule.class;
		oArr[1] = new TextAttribute(new Color(Display.getCurrent(), ColorConstants.XML_PROC_INSTR));
		contentTypes.put(CT_XML_PROC_INSTR, oArr);
		
		//add required data for content type XML_COMMENT
		oArr = new Object[2];
		oArr[0] = XMLCommentRule.class;
		oArr[1] = new TextAttribute(new Color(Display.getCurrent(), ColorConstants.XML_COMMENT));
		contentTypes.put(CT_XML_COMMENT, oArr);
		
		//add required data for content type XML_TAG
		oArr = new Object[2];
		oArr[0] = XMLTagRule.class;
		oArr[1] = new TextAttribute(new Color(Display.getCurrent(), ColorConstants.XML_TAG));
		contentTypes.put(CT_XML_TAG, oArr);
		
		//add required data for content type XML_STRING_SINGLE_QUOTED
		oArr = new Object[2];
		oArr[0] = XMLStringSingleQuotedRule.class;
		oArr[1] = new TextAttribute(new Color(Display.getCurrent(), ColorConstants.XML_STRING));
		contentTypes.put(CT_XML_STRING_SINGLE_QUOTED, oArr);
		
		//add required data for content type XML_STRING_DOUBLE_QUOTED
		oArr = new Object[2];
		oArr[0] = XMLStringDoubleQuotedRule.class;
		oArr[1] = new TextAttribute(new Color(Display.getCurrent(), ColorConstants.XML_STRING));
		contentTypes.put(CT_XML_STRING_DOUBLE_QUOTED, oArr);
	}
	
	public enum TokenType {
		CONTENT_TYPE,
		TEXT_ATTRIBUTES
	}

	private List<IPredicateRule> rules;

	public XMLScanner(TokenType tokenType) {
		rules = new ArrayList<IPredicateRule>();
		for (Iterator<String> contentTypesIter = contentTypes.keySet()
				.iterator(); contentTypesIter.hasNext();) {
			String curContentType = contentTypesIter.next();
			rules.add(getRuleForContentType(curContentType, tokenType));

		}

		setPredicateRules(rules.toArray(new IPredicateRule[0]));
	}

	private static IPredicateRule getRuleForContentType(String contentType, TokenType tokenType) {
		Object ruleInstance = null;
		if (contentTypes.containsKey(contentType)) {
			IToken token = getTokenForContentType(contentType, tokenType);
			Class<?> paramTypes[] = new Class<?>[1];
			paramTypes[0] = IToken.class;

			Class<?> ruleClass = (Class<?>) contentTypes.get(contentType)[0];
			try {
				Constructor<?> c = ruleClass.getConstructor(IToken.class);
				ruleInstance = c.newInstance(new Object[] { token });
			} catch (Exception e) {
				// ignore rule if it can not be instantiated
			}
		}
		
		if ((ruleInstance != null) &&(ruleInstance instanceof IPredicateRule)) {
			return (IPredicateRule) ruleInstance;
		} else {
			return null;
		}

	}

	private static IToken getTokenForContentType(String contentType, TokenType tokenType) {
		switch (tokenType) {
		case CONTENT_TYPE:
			return new Token(contentType);	
		case TEXT_ATTRIBUTES:
			return new Token(contentTypes.get(contentType)[1]);

		default:
			return null;
		}
		
	}

	public String[] getSupportedContentTypes() {
		return contentTypes.keySet().toArray(new String[0]);
	}

}
