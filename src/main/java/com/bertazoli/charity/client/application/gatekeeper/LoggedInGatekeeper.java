package com.bertazoli.charity.client.application.gatekeeper;

import com.bertazoli.charity.client.application.events.login.LoginAuthenticatedEvent;
import com.bertazoli.charity.client.application.events.login.LoginAuthenticatedEventHandler;
import com.bertazoli.charity.shared.beans.User;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;

@Singleton
public class LoggedInGatekeeper implements Gatekeeper {

    private EventBus eventBus;
    private User currentUser;

    @Inject
    public LoggedInGatekeeper(EventBus eventBus) {
        this.eventBus = eventBus;

        this.eventBus.addHandler(LoginAuthenticatedEvent.getType(), new LoginAuthenticatedEventHandler() {
            @Override
            public void onLogin(LoginAuthenticatedEvent event) {
                currentUser = event.getUser();
            }
        });
    }

    @Override
    public boolean canReveal() {
        return (currentUser != null && currentUser.isLoggedIn());
    }
}
