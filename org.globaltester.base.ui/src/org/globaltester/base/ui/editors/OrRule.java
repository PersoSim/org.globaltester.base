package org.globaltester.base.ui.editors;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

/**
 * IPredicateRule tries to match one of a set of rules. If either rule matches
 * the successToken is returned.
 * 
 * @author amay
 * 
 */
public class OrRule implements IRule, IPredicateRule {

	IToken token;
	Set<IPredicateRule> rules;

	public OrRule(Set<IPredicateRule> rules, IToken token) {

		this.token = token;
		this.rules = rules;

	}

	public OrRule(IToken token) {
		this.token = token;
		this.rules = new HashSet<IPredicateRule>();
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner) {
		return evaluate(scanner, false);
	}
	
	public boolean addRule(IPredicateRule newRule){
		return rules.add(newRule);
	}

	@Override
	public IToken getSuccessToken() {
		return token;
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		for (Iterator<IPredicateRule> ruleIter = rules.iterator(); ruleIter.hasNext();) {
			IPredicateRule curRule = ruleIter.next();
			
			if (curRule.getSuccessToken().equals(curRule.evaluate(scanner, resume))){
				return token;
			}
		}

		return Token.UNDEFINED;
	}

}
