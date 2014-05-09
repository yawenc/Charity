package com.bertazoli.charity.client.application.callback;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.widget.core.client.Popup;

public abstract class CustomAsyncCallback<T> implements AsyncCallback<T> {
    

    @Override
    public void onFailure(Throwable caught) {
        Label label = new Label(caught.getMessage());
        Popup popup = new Popup();
        popup.add(label);
        popup.center();
        popup.show();
    }

    @Override
    abstract public void onSuccess(T result);
}
