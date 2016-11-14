package org.globaltester.base;

/**
 * This represents a property using a key and a value. The value can have a
 * human readable representation that can be used i.e. the user interface.
 * 
 * @author mboonk
 *
 */
public class PropertyElement {
	private String key;
	private Object value;
	private String representation;

	/**
	 * @param name
	 *            the name of the property
	 * @param value
	 *            the actual value of the property
	 * @param representation
	 *            a simplified, human readable representation
	 */
	public PropertyElement(String name, Object value, String representation) {
		super();
		this.key = name;
		this.value = value;
		this.representation = representation;
	}

	/**
	 * @return the key for this element
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @return the value of this element
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @return the human readable representation of this element
	 */
	public String getRepresentation() {
		return representation;
	}
}
