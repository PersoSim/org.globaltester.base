package org.globaltester.base.ui.editors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

/**
 * IRule that tries to match the scanner against a regular expression pattern.
 * 
 * Returns after the first match found in the scanner, this means only the first
 * step of repetitive regex will be found
 * 
 * !!! WARNING !!!
 * Matching regular expressions is highly expensive.
 * Use this rule only if you know what you are doing.
 * 
 * @author amay
 * 
 */
public class RegexRule implements IRule, IPredicateRule {

	IToken token;
	Pattern pattern;

	public RegexRule(String pattern, IToken token) {
		this.token = token;
		this.pattern = Pattern.compile(pattern);
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner) {
		StringBuilder stream = new StringBuilder();
		
		int curChar;
		int count = 0;
		
		Matcher m;
		do {
			m = pattern.matcher(stream);
			if (m.matches()) {
				return token;
			}
			
			//suggestion for performance improvement:
			//read several characters as block in advance, e.g. 10, then match and either unread
			//surplus chars or read next block - saves on executions of matching
			curChar = scanner.read();
			count++;
			
			stream.append((char) curChar);
			
		} while (!(curChar == '\n' || curChar == '\r' || curChar == ICharacterScanner.EOF));
		
		// if not matched unread the scanner count times
		for (int i = 0; i < count; i++) {
			scanner.unread();
		}
		
		return Token.UNDEFINED;
		
	}

	@Override
	public IToken getSuccessToken() {
		return token;
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		// resuming is not supported by this rule and therfore ignored
		return evaluate(scanner);
	}

}
