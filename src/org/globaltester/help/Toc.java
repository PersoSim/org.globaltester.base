package org.globaltester.help;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.help.IToc;
import org.eclipse.help.ITopic;
import org.eclipse.help.IUAElement;

public class Toc implements IToc {

	protected String label = "";
	protected String href = "";
	protected boolean enabled = true;
	protected ArrayList<ITopic> topics = new ArrayList<ITopic>();
	ITopic[] emptyTopics = new ITopic[]{};

	public Toc(String nLabel) {
		this(nLabel, "", true, null);
	}
	
	public Toc(String nLabel, String nHref) {
		this(nLabel, nHref, true, null);
	}
	
	public Toc(String nLabel, String nHref, boolean nEnabled) {
		this(nLabel, nHref, nEnabled, null);
	}
	
	public Toc(String nLabel, String nHref, boolean nEnabled, Collection<? extends ITopic> subTopics) {
		label = nLabel;
		href = nHref;
		enabled = nEnabled;
		if (subTopics != null) {
			topics.addAll(subTopics);
		}
	}
	
	@Override
	public boolean isEnabled(IEvaluationContext context) {
		return enabled ;
	}

	@Override
	public IUAElement[] getChildren() {
		return topics.toArray(emptyTopics);
	}

	@Override
	public String getHref() {
		return href;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public ITopic[] getTopics() {
		return topics.toArray(emptyTopics);
	}

	@Override
	public ITopic getTopic(String href) {
		if (href == null) {
			return null;
		}
		for (ITopic topic : topics) {
			if (topic != null && href.equals(topic.getHref())) {
				return topic;
			}
		}
		return null;
	}

}
