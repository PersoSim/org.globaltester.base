package org.globaltester.base.ui.editors;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.WordRule;

/**
 * WordRule that matches all JavaScript keywords.
 * @author amay
 *
 */
public class JsKeyWordRule extends WordRule implements IPredicateRule {
	private IToken token;

	public JsKeyWordRule(IToken token) {
		super(new JsKeyWordDetector());
		this.token = token;
		
		addJsKeywords();
	}
	
	private void addJsKeywords(){
		this.addWord("load", token);
		this.addWord("print", token);
		this.addWord("assert", token);

		this.addWord("break", token);
		this.addWord("case", token);
		this.addWord("catch", token);
		this.addWord("const", token);
		this.addWord("continue", token);
		this.addWord("default", token);
		this.addWord("delete", token);
		this.addWord("do", token);
		this.addWord("else", token);
		this.addWord("export", token);
		this.addWord("false", token);
		this.addWord("for", token);
		this.addWord("function", token);
		this.addWord("if", token);
		this.addWord("in", token);
		this.addWord("instanceof", token);
		this.addWord("new", token);
		this.addWord("null", token);
		this.addWord("return", token);
		this.addWord("switch", token);
		this.addWord("then", token);
		this.addWord("this", token);
		this.addWord("throw", token);
		this.addWord("true", token);
		this.addWord("try", token);
		this.addWord("typeof", token);
		this.addWord("var", token);
		this.addWord("void", token);
		this.addWord("while", token);
		this.addWord("with", token);
	}

	@Override
	public IToken getSuccessToken() {
		return token;
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		return evaluate(scanner);
	}

}

class JsKeyWordDetector implements IWordDetector {
	@Override
	public boolean isWordPart(char c){
		return c == '-' || Character.isLetter(c) || Character.isDigit(c);
	}
	
	@Override
	public boolean isWordStart(char c){
		return c == '-' || Character.isLetter(c);
	}

}
