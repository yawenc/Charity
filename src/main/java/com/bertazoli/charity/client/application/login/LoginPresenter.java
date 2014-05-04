package com.bertazoli.charity.client.application.login;

import com.bertazoli.charity.client.application.callback.CustomAsyncCallback;
import com.bertazoli.charity.client.application.events.login.LoginAuthenticatedEvent;
import com.bertazoli.charity.client.place.NameTokens;
import com.bertazoli.charity.shared.action.LoginAction;
import com.bertazoli.charity.shared.action.LoginResult;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.HasSelectHandlers;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

@NoGatekeeper
public class LoginPresenter extends Presenter<LoginPresenter.MyView, LoginPresenter.MyProxy> {
    interface MyView extends View {
        HasSelectHandlers getSubmitButton();
        boolean validate();
        String getPassword();
        String getUsername();
        void setFocusToUsername();
    }
    
    @Inject PlaceManager placeManager;

    @NameToken(NameTokens.login)
    @ProxyStandard
    public interface MyProxy extends ProxyPlace<LoginPresenter> {}

    private DispatchAsync dispatcher;

    @Inject
    public LoginPresenter(EventBus eventBus, MyView view, MyProxy proxy, DispatchAsync dispatcher) {
        super(eventBus, view, proxy, RevealType.Root);
        this.dispatcher = dispatcher;
    }

    @Override
    protected void onBind() {
        super.onBind();
        
        registerHandler(getView().getSubmitButton().addSelectHandler(new SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                if (getView().validate()) {
                    doLogin();
                }
            }
        }));
    }
    
    @Override
    public void prepareFromRequest(PlaceRequest request) {
        getView().setFocusToUsername();
    }
    
    private void doLogin() {
        dispatcher.execute(new LoginAction(getView().getUsername(), getView().getPassword()), new CustomAsyncCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult result) {
                if (result != null) {
                    getEventBus().fireEvent(new LoginAuthenticatedEvent(result.getSessionKey(), result.getUser()));
                    PlaceRequest request = new PlaceRequest.Builder().nameToken(NameTokens.home).build();
                    placeManager.revealPlace(request);
                }
            }
        });
    }
}
