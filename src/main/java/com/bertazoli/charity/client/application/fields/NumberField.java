package com.bertazoli.charity.client.application.fields;

import com.bertazoli.charity.client.application.validation.ValidationResult;
import com.bertazoli.charity.client.application.validation.Validator;
import com.bertazoli.charity.shared.util.Util;
import com.google.gwt.i18n.client.NumberFormat;

public class NumberField extends Validator {

    private NumberFormat numberFormat;

	@Override
    public ValidationResult validate() {
        if (!isEmptyAllowed() && Util.isNullOrEmpty(field.getValue())) {
            validationResult.setError(field, "Error");
            return validationResult;
        }
        
        if (!field.getValue().matches("^\\d*$")) {
            validationResult.setError(field, "Error");
            return validationResult;
        }
        
        validationResult.setError(false);
        return validationResult;
    }

	public void setFormat(NumberFormat numberFormat) {
		this.numberFormat = numberFormat;
	}

	@Override
	public void reformat(String text) {
		if (numberFormat != null) {
			field.setText(numberFormat.format(Double.parseDouble(text)));
		}
	}
}
