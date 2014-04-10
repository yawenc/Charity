package com.bertazoli.charity.client.application.fields;

import com.bertazoli.charity.client.application.validation.ValidationResult;
import com.bertazoli.charity.client.application.validation.Validator;
import com.bertazoli.charity.shared.util.Util;

public class EmailField extends Validator {

    @Override
    public ValidationResult validate() {
        if (!isEmptyAllowed() && Util.isNullOrEmpty(field.getValue())) {
            validationResult.setError(field, "Error");
            return validationResult;
        }
        
        if (!field.getText().matches(".+@.+\\.[a-zA-Z]+")) {
            validationResult.setError(field, "Error");
            return validationResult;
        }
        
        validationResult.setError(false);
        return validationResult;
    }

	@Override
	public void reformat(String text) {
		// TODO Auto-generated method stub
		
	}

}
