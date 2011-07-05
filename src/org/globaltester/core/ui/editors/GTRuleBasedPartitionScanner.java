package org.globaltester.core.ui.editors;

import java.util.ArrayList;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;

public class GTRuleBasedPartitionScanner extends RuleBasedPartitionScanner {
	
	public enum TokenType {
		CONTENT_TYPE,
		TEXT_ATTRIBUTES
	}
	
	protected ArrayList<IPredicateRule> rules = new ArrayList<IPredicateRule>();

	public String[] getLegalContentTypes() {
		IPredicateRule[] ruleArray = rules.toArray(new IPredicateRule[]{});
		String[] contentTypes = new String[ruleArray.length];
		
		for (int i = 0; i < ruleArray.length; i++) {
			Object o = ruleArray[i].getSuccessToken().getData();
			if (o instanceof String) {
				contentTypes[i] = (String) o;	
			} else {
				contentTypes[i] = null;
			}
			
		}
		
		return contentTypes;
	}

	public void addPredicateRule(IPredicateRule ruleForContentType) {
		rules.add(ruleForContentType);
		setPredicateRules(rules.toArray(new IPredicateRule[]{}));
	}

}
