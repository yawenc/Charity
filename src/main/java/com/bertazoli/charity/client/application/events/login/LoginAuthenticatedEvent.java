package com.bertazoli.charity.client.application.events.login;

import com.bertazoli.charity.shared.beans.User;
import com.google.gwt.event.shared.GwtEvent;

public class LoginAuthenticatedEvent extends GwtEvent<LoginAuthenticatedEventHandler> {
    private User user;

    private static final Type<LoginAuthenticatedEventHandler> TYPE = new Type<LoginAuthenticatedEventHandler>();

    public LoginAuthenticatedEvent(User user) {
        this.user = user;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
