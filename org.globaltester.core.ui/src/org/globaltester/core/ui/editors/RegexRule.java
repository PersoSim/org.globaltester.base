package org.globaltester.core.ui.editors;

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
