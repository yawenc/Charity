package com.bertazoli.charity.client.gin;

import com.bertazoli.charity.client.application.events.login.LoginAuthenticatedEvent;
import com.bertazoli.charity.shared.action.user.FetchUserFromSessionAction;
import com.bertazoli.charity.shared.action.user.FetchUserFromSessionResult;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Bootstrapper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

public class BootstraperImpl implements Bootstrapper {
    
    private DispatchAsync dispatcher;
    private EventBus eventBus;
    private PlaceManager placeManager;

    @Inject
    public BootstraperImpl(DispatchAsync dispatcher, EventBus eventBus, PlaceManager placeManager) {
        this.dispatcher = dispatcher;
        this.eventBus = eventBus;
        this.placeManager = placeManager;
    }

    @Override
    public void onBootstrap() {
        checkForUserSession();
    }

    private void checkForUserSession() {
        final String sessionID = Cookies.getCookie("JSESSIONID");
        if (sessionID != null) {
            dispatcher.execute(new FetchUserFromSessionAction(sessionID), new AsyncCallback<FetchUserFromSessionResult>() {
                @Override
                public void onFailure(Throwable caught) {
                    placeManager.revealCurrentPlace();
                }

                @Override
                public void onSuccess(FetchUserFromSessionResult result) {
                    if (result != null && result.getUser() != null && result.getUser().isLoggedIn()) {
                        eventBus.fireEvent(new LoginAuthenticatedEvent(sessionID, result.getUser()));
                    }
                    placeManager.revealCurrentPlace();
                }
            });
        } else {
            placeManager.revealCurrentPlace();
        }
    }
}
