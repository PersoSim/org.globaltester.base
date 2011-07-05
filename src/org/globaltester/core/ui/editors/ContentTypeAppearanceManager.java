package org.globaltester.core.ui.editors;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * This class manages the content types and the associated appearance for all
 * defined tokens
 * 
 * @author Alexander May
 * 
 */

public class ContentTypeAppearanceManager {
	public static final String CONTENT_TYPE_DEFAULT = "__DEFAULT";
	
	public final static String CONTENT_TYPE_JS = "__JS";
	public final static String CONTENT_TYPE_JS_KEYWORD = "__JS_KEYWORD";
	public final static String CONTENT_TYPE_JS_COMMENT = "__JS_COMMENT";
	public final static String CONTENT_TYPE_JS_MULTILINE_COMMENT = "__JS_MULTILINE_COMMENT";
	public final static String CONTENT_TYPE_JS_SINGLELINE_COMMENT = "__JS_SINGLELINE_COMMENT";

	public final static String CONTENT_TYPE_XML = "__XML";
	public static final String CONTENT_TYPE_XML_PROC_INSTR = "__XML_PROC_INSTR";
	public final static String CONTENT_TYPE_XML_COMMENT = "__XML_COMMENT";
	public final static String CONTENT_TYPE_XML_TAG = "__XML_TAG";
	
	

	protected Map<RGB, Color> fColorTable = new HashMap<RGB, Color>(10);
	private static Map<String, IToken> contentTypeTokenTable = new HashMap<String, IToken>();
	private Map<String, IToken> contentTypeAppearanceTable = new HashMap<String, IToken>();

	public void dispose() {
		Iterator<Color> e = fColorTable.values().iterator();
		while (e.hasNext())
			e.next().dispose();
	}

	public Color getColor(RGB rgb) {
		Color color = fColorTable.get(rgb);
		if (color == null) {
			color = new Color(Display.getCurrent(), rgb);
			fColorTable.put(rgb, color);
		}
		return color;
	}

	/**
	 * Return a (singleton) token that represents the queried content type if
	 * the contenttype is managed by this class, otherwise null is returned
	 * 
	 * @param contentType
	 * @return
	 */
	public static IToken getContentTypeToken(String contentType) {
		IToken token = contentTypeTokenTable.get(contentType);
		if ((token == null) && (isManagedContentType(contentType))) {
			token = new Token(contentType);
			contentTypeTokenTable.put(contentType, token);
		}
		return token;
	}

	/**
	 * returns true if the given string identifies a content type that is
	 * managed by this class
	 * 
	 * @param contentType
	 * @return
	 */
	private static boolean isManagedContentType(String contentType) {
		if (CONTENT_TYPE_DEFAULT.equals(contentType)) return true;
		
		if (CONTENT_TYPE_JS.equals(contentType)) return true;
		if (CONTENT_TYPE_JS_KEYWORD.equals(contentType)) return true;
		if (CONTENT_TYPE_JS_COMMENT.equals(contentType)) return true;
		if (CONTENT_TYPE_JS_MULTILINE_COMMENT.equals(contentType)) return true;
		if (CONTENT_TYPE_JS_SINGLELINE_COMMENT.equals(contentType)) return true;

		if (CONTENT_TYPE_XML.equals(contentType)) return true;
		if (CONTENT_TYPE_XML_COMMENT.equals(contentType)) return true;
		if (CONTENT_TYPE_XML_TAG.equals(contentType)) return true;
		
		return false;
	}

	public IToken getContentTypeAppearance(String contentType) {
		IToken token = contentTypeAppearanceTable.get(contentType);
		
		//return as quick ass possible
		if (token != null) return token;
		
		//create token if not jet created
		if (CONTENT_TYPE_XML_COMMENT.equals(contentType)) {
			token = new Token(new TextAttribute(
					getColor(ColorConstants.XML_COMMENT)));
			contentTypeAppearanceTable.put(contentType, token);
		} else if (CONTENT_TYPE_XML_PROC_INSTR.equals(contentType)) {
			token = new Token(new TextAttribute(
					getColor(ColorConstants.XML_PROC_INSTR)));
			contentTypeAppearanceTable.put(contentType, token);
		} else if (CONTENT_TYPE_XML_TAG.equals(contentType)) {
			token = new Token(new TextAttribute(
					getColor(ColorConstants.XML_TAG)));
			contentTypeAppearanceTable.put(contentType, token);
		} else if (CONTENT_TYPE_JS.equals(contentType)) {
			token = new Token(new TextAttribute(
					getColor(ColorConstants.JS_KEYWORD)));
			contentTypeAppearanceTable.put(contentType, token);
		} else {
			//FIXME remove the print statement below 
			//TODO reconsider if this branch should return an exception or warning
			System.out.println("DEFAULT Appearance Token generated for content type \""+contentType+"\"");
			token = new Token(new TextAttribute(
					getColor(ColorConstants.UNKNOWN_CONTENT)));
			contentTypeAppearanceTable.put(contentType, token);
		}
		
		//FIXME remove the print statement below
		System.out.println("Appearance Token generated for content type \""+contentType+"\"");
		
		
		return token;
		
	}
}
