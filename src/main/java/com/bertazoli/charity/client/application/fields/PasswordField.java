package com.bertazoli.charity.client.application.fields;

import com.bertazoli.charity.client.application.validation.ValidationResult;
import com.bertazoli.charity.client.application.validation.Validator;
import com.bertazoli.charity.shared.util.Util;

public class PasswordField extends Validator implements HasMaxLength, HasMinLength {
    private Integer minLength = -1;
    private Integer maxLength = -1;
    
    @Override
    public ValidationResult validate() {
        if (!isEmptyAllowed() && Util.isNullOrEmpty(field.getValue())) {
            validationResult.setError(field, "Error");
            return validationResult;
        }
        
        if (minLength != -1 && field.getText().length() < minLength) {
            validationResult.setError(field, "Error");
            return validationResult;
        }
        
        if (maxLength != -1 && field.getText().length() > maxLength) {
            validationResult.setError(field, "Error");
            return validationResult;
        }
        
        validationResult.setError(false);
        return validationResult;
    }

    @Override
    public void setMinLength(Integer length) {
        this.minLength = length;
    }

    @Override
    public Integer getMinLength() {
        return minLength;
    }

    @Override
    public void setMaxLength(Integer length) {
        this.maxLength = length;
    }

    @Override
    public Integer getMaxLength() {
        return maxLength;
    }

	@Override
	public void reformat(String text) {
		// TODO Auto-generated method stub
		
	}
}
