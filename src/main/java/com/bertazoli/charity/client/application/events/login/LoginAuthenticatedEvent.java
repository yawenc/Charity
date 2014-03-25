package com.bertazoli.charity.client.application.events.login;

import com.google.gwt.event.shared.GwtEvent;

public class LoginAuthenticatedEvent extends GwtEvent<LoginAuthenticatedEventHandler> {

    private static final Type<LoginAuthenticatedEventHandler> TYPE = new Type<LoginAuthenticatedEventHandler>();

    @Override
    public Type<LoginAuthenticatedEventHandler> getAssociatedType() {
        return getType();
    }

    public static Type<LoginAuthenticatedEventHandler> getType() {
        return TYPE;
    }

    @Override
    protected void dispatch(LoginAuthenticatedEventHandler handler) {
        handler.onLogin(this);
    }
}
