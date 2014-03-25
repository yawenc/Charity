package com.bertazoli.charity.client.application.events.login;

import com.google.gwt.event.shared.EventHandler;

public interface LoginAuthenticatedEventHandler extends EventHandler {
    void onLogin(LoginAuthenticatedEvent event);
}
