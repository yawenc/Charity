package com.bertazoli.charity.client.application.validation;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.ValueBoxBase;
import com.google.inject.Inject;

public abstract class Validator {
    
    protected ValueBoxBase<String> field;
    protected ValidationResult validationResult = new ValidationResult();
    protected boolean emptyAllowed = false;
    
    @Inject
    public Validator() {
        
    }

    public void setField(ValueBoxBase<String> field, boolean emptyAllowed) {
        this.field = field;
        this.emptyAllowed = emptyAllowed;
        
        setValueChangeHandlers();
    }
    
    protected void setValueChangeHandlers() {
        if (field != null) {
            field.addValueChangeHandler(new ValueChangeHandler<String>() {
                @Override
                public void onValueChange(ValueChangeEvent<String> event) {
                    validate();
                    if (validationResult.isError()) {
                        validationResult.showError();
                    } else {
                        validationResult.clear();
                        reformat(event.getValue());
                    }
                }
            });
        }
    }

    public abstract ValidationResult validate();
    
    public abstract void reformat(String text);

    public boolean isEmptyAllowed() {
        return emptyAllowed;
    }

    public void setEmptyAllowed(boolean emptyAllowed) {
        this.emptyAllowed = emptyAllowed;
    }
}
