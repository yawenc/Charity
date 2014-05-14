package com.bertazoli.charity.client.application.gatekeeper;

import com.bertazoli.charity.client.application.events.login.LoginAuthenticatedEvent;
import com.bertazoli.charity.client.application.events.login.LoginAuthenticatedEventHandler;
import com.bertazoli.charity.client.application.security.SecurityManager;
import com.bertazoli.charity.shared.beans.User;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;

@Singleton
public class LoggedInGatekeeper implements Gatekeeper {

    private EventBus eventBus;
    protected User currentUser;
    private SecurityManager security;

    @Inject
    public LoggedInGatekeeper(EventBus eventBus, final SecurityManager security, DispatchAsync dispatchAsync) {
        this.eventBus = eventBus;
        this.security = security;

        this.eventBus.addHandler(LoginAuthenticatedEvent.getType(), new LoginAuthenticatedEventHandler() {
            @Override
            public void onLogin(LoginAuthenticatedEvent event) {
                if (event != null) {
                    currentUser = event.getUser();
                    security.setUser(currentUser);
                } else {
                    currentUser = null;
                    security.setUser(null);
                }
            }
        });
    }

    @Override
    public boolean canReveal() {
        return (currentUser != null && currentUser.isLoggedIn());
    }
}
