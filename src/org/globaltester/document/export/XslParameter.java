package org.globaltester.document.export;

public class XslParameter {
	private String name;
	private Object value;
	
	public XslParameter(String name, Object value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}
}
