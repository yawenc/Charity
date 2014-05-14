package com.bertazoli.charity.client.application.gatekeeper;

import com.bertazoli.charity.client.application.events.login.LoginAuthenticatedEvent;
import com.bertazoli.charity.client.application.events.login.LoginAuthenticatedEventHandler;
import com.bertazoli.charity.client.application.security.SecurityManager;
import com.bertazoli.charity.shared.beans.User;
import com.bertazoli.charity.shared.beans.enums.UserRole;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;

@Singleton
public class AdminGateKeeper implements Gatekeeper {

    protected User currentUser;

    @Inject
    public AdminGateKeeper(EventBus eventBus, final SecurityManager security, DispatchAsync dispatchAsync) {
        eventBus.addHandler(LoginAuthenticatedEvent.getType(), new LoginAuthenticatedEventHandler() {
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
        return (currentUser != null && currentUser.isLoggedIn() && currentUser.getUserRole() == UserRole.ADMIN);
    }
}
