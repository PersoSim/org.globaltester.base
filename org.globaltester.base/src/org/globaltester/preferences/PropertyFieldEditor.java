package org.globaltester.preferences;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * A field editor for a java properties type string preference.
 * <p>
 * Multiline content will be automatically concatenated with spaces to form a
 * single line.
 * </p>
 */
public class PropertyFieldEditor extends FieldEditor {

	private Text textField;
	private Object oldValue;

	/**
	 * Creates a property field editor.
	 * 
	 * @param name
	 *            the name of the preference this field editor works on
	 * @param labelText
	 *            the label text of the field editor
	 * @param parent
	 *            the parent of the field editor's control
	 * @since 2.0
	 */
	public PropertyFieldEditor(String name, String labelText, Composite parent) {
		init(name, labelText);
		createControl(parent);
	}

	@Override
	protected void adjustForNumColumns(int numColumns) {
		GridData gdLabel = (GridData) getLabelControl().getLayoutData();
		gdLabel.horizontalSpan = numColumns;

		GridData gdTextField = (GridData) getTextControl().getLayoutData();
		gdTextField.horizontalSpan = numColumns;
	}

	@Override
	protected void doFillIntoGrid(Composite parent, int numColumns) {
		Label label = getLabelControl(parent);
		GridData gdLabel = new GridData();
		gdLabel.horizontalSpan = numColumns;
		gdLabel.horizontalAlignment = GridData.BEGINNING;
		gdLabel.grabExcessHorizontalSpace = true;
		label.setLayoutData(gdLabel);

		textField = getTextControl(parent);
		GridData gdTextField = new GridData();
		gdTextField.horizontalSpan = numColumns;
		gdTextField.horizontalAlignment = GridData.FILL;
		gdTextField.grabExcessHorizontalSpace = true;
		gdTextField.heightHint = 50;
		gdTextField.widthHint = 450;
		textField.setLayoutData(gdTextField);
	}

	public Text getTextControl(Composite parent) {
		if (textField == null) {
			textField = new Text(parent, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL
					| SWT.BORDER);
			textField.setFont(parent.getFont());
			
			textField.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    valueChanged();
                }
            });
            textField.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    valueChanged();
                }
            });

		} else {
			checkParent(textField, parent);
		}
		return textField;
	}

	protected void valueChanged() {
		String newValue = textField.getText();
        if (!newValue.equals(oldValue)) {
            fireValueChanged(VALUE, oldValue, newValue);
            oldValue = newValue;
        }
	}

	protected void doLoad() {
		if (textField != null) {
			String value = getPreferenceStore().getString(getPreferenceName());
			textField.setText(value.replaceAll(" ", "\n"));
			oldValue = textField.getText();
		}
	}

	protected void doLoadDefault() {
		if (textField != null) {
			String value = getPreferenceStore().getDefaultString(
					getPreferenceName());
			textField.setText(value.replaceAll(" ", "\n"));
			oldValue = textField.getText();
		}
	}

	protected void doStore() {
		getPreferenceStore().setValue(getPreferenceName(),
				textField.getText().replaceAll("[\r\n]+", " "));
	}

	@Override
	public int getNumberOfControls() {
		return 3;
	}

	/**
	 * Returns this field editor's text control.
	 * 
	 * @return the text control, or <code>null</code> if no text field is
	 *         created yet
	 */
	protected Text getTextControl() {
		return textField;
	}
	
	@Override
	public void setEnabled(boolean enabled, Composite parent) {
        super.setEnabled(enabled, parent);
        getTextControl(parent).setEnabled(enabled);
    }
}