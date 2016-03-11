package org.globaltester.base.doc.help;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.help.ITopic;
import org.eclipse.help.IUAElement;

public class Topic implements ITopic {

	protected String label = "";
	protected String href = "";
	protected boolean enabled = true;
	protected ArrayList<ITopic> subTopics = new ArrayList<ITopic>();
	ITopic[] emptyTopics = new ITopic[] {};

	public Topic(String nLabel) {
		this(nLabel, "", true, null);
	}

	public Topic(String nLabel, String nHref) {
		this(nLabel, nHref, true, null);
	}

	public Topic(String nLabel, String nHref, boolean nEnabled) {
		this(nLabel, nHref, nEnabled, null);
	}

	public Topic(String nLabel, String nHref, boolean nEnabled,
			Collection<? extends ITopic> nTopics) {
		label = nLabel;
		href = nHref;
		enabled = nEnabled;
		if (nTopics != null) {
			subTopics.addAll(nTopics);
		}
	}

	@Override
	public boolean isEnabled(IEvaluationContext context) {
		return true;
	}

	@Override
	public IUAElement[] getChildren() {
		return subTopics.toArray(emptyTopics);
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
	public ITopic[] getSubtopics() {
		return subTopics.toArray(emptyTopics);
	}

	public void addSubtopic(Topic topic) {
		if (topic != null) {
			subTopics.add(topic);
		}

	}

}
