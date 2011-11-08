package org.globaltester.core.ui.editors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

/**
 * IRule that tries to match the scanner against a
 * regular expression pattern.
 * 
 * @author amay
 * 
 */
public class RegexRule implements IRule {

	IToken token;
	Pattern pattern;
	
	public RegexRule(String pattern, IToken token) {

		this.token = token;
		this.pattern = Pattern.compile(pattern);

	}

	public IToken evaluate(ICharacterScanner scanner) {

		String stream = "";
		int curChar;
		int count = 0;
		
		do {
			Matcher m = pattern.matcher(stream);
			if (m.matches()) {
				return token;
			}

			curChar = scanner.read();
			count++;
			stream += (char) curChar;


		} while (!(curChar == '\n' || curChar == '\r' || curChar == ICharacterScanner.EOF));
		
		// if not matched unread the scanner count times
		for (int i = 0; i < count; i++) {
			scanner.unread();
		}

		return Token.UNDEFINED;

	}

}
