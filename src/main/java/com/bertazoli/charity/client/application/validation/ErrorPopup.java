package com.bertazoli.charity.client.application.validation;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class ErrorPopup extends PopupPanel {
    private String message;
    private Widget field;

    public void setErrorMessage(String message) {
        this.message = message;
    }

    public void setPosition(Widget field) {
        this.field = field;
    }

    public void displayError() {
        clear();
        HTMLPanel div = new HTMLPanel(message);
        setStyleName("error box paddingLeft10 paddingRight10");
        add(div);
        setPopupPosition(field.getAbsoluteLeft() + field.getOffsetWidth(), field.getAbsoluteTop());
        show();
    }
}
