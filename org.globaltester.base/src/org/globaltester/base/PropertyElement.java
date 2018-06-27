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
	private String value;
	private String representation;
	private boolean hidden;

	/**
	 * @param name
	 *            the name of the property
	 * @param value
	 *            the actual value of the property
	 * @param representation
	 *            a simplified, human readable representation
	 * @param hidden
	 *            this field is not essential and can be hidden to the user
	 */
	public PropertyElement(String name, String value, String representation, boolean hidden) {
		super();
		this.key = name;
		this.value = value;
		this.representation = representation;
		this.hidden = hidden;
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
	public String getValue() {
		return value;
	}

	/**
	 * @return the human readable representation of this element
	 */
	public String getRepresentation() {
		return representation;
	}

	/**
	 * @return the human readable representation of this element
	 */
	public boolean isHidden() {
		return hidden;
	}

	@Override
	public String toString() {
		return "PropertyElement [key=" + key + ", value=" + value + ", representation=\"" + representation + "\", hidden=\"" + hidden + "\"]";
	}
	
	
}
