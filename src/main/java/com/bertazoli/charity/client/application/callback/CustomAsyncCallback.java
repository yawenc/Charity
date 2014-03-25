package com.bertazoli.charity.client.application.callback;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class CustomAsyncCallback<T> implements AsyncCallback<T> {

    @Override
    public void onFailure(Throwable caught) {
        // TODO handle the exception gracefully
        System.out.println(caught.getMessage());
    }

    @Override
    abstract public void onSuccess(T result);
}
