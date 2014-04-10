package com.bertazoli.charity.client.application.validation;

import com.google.gwt.user.client.ui.Widget;

public class ValidationResult {
    
    private boolean error = false;
    private Widget field;
    private String message;
    private ErrorPopup errorPopup;
    
    public void clear() {
        error = false;
        message = "";
        field = null;
        
        if (errorPopup != null) {
            errorPopup.hide();
        }
    }

    public void setError(Widget field, String message) {
        error = true;
        this.field = field;
        this.message = message;
        
        if (errorPopup == null) {
            errorPopup = new ErrorPopup();
        }
    }

    public boolean isError() {
        return error;
    }
    
    public void showError() {
        if (errorPopup != null) {
            errorPopup.setErrorMessage(message);
            errorPopup.setPosition(field);
            errorPopup.displayError();
        }
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
