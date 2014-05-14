package com.bertazoli.charity.client.application.header;

import com.bertazoli.charity.client.application.callback.CustomAsyncCallback;
import com.bertazoli.charity.client.application.events.login.LoginAuthenticatedEvent;
import com.bertazoli.charity.client.application.security.SecurityManager;
import com.bertazoli.charity.client.place.NameTokens;
import com.bertazoli.charity.shared.action.LogoutAction;
import com.bertazoli.charity.shared.action.LogoutResult;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.HasSelectHandlers;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class HeaderPresenter extends PresenterWidget<HeaderPresenter.MyView> {
    public interface MyView extends View {
        void setTotalDonation(Double total);
        HasSelectHandlers getLogoutButton();
        HasSelectHandlers getUserSettingsButton();
    }
    
    private SecurityManager security;
    @Inject private DispatchAsync dispatcher;
    @Inject private PlaceManager placeManager;

    @Inject
    public HeaderPresenter(EventBus eventBus, MyView view, SecurityManager security) {
        super(eventBus, view);
        this.security = security;
    }

    public void setTotalDonation(Double total) {
        getView().setTotalDonation(total);
    }
    
    @Override
    protected void onBind() {
        registerHandler(getView().getLogoutButton().addSelectHandler(new SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                dispatcher.execute(new LogoutAction(), new CustomAsyncCallback<LogoutResult>() {
                    @Override
                    public void onSuccess(LogoutResult result) {
                        getEventBus().fireEvent(new LoginAuthenticatedEvent(null,null));
                        PlaceRequest request = new PlaceRequest.Builder().nameToken(NameTokens.getLogin()).build();
                        placeManager.revealPlace(request);
                    }
                });
            }
        }));
        
        registerHandler(getView().getUserSettingsButton().addSelectHandler(new SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                PlaceRequest request = new PlaceRequest.Builder().nameToken(NameTokens.userSettings).build();
                placeManager.revealPlace(request);
            }
        }));
    }
}
