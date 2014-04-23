package com.bertazoli.charity.client.application.signup.usercreated;

import com.bertazoli.charity.client.place.NameTokens;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.HasSelectHandlers;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class UserCreatedPresenter extends Presenter<UserCreatedPresenter.MyView, UserCreatedPresenter.MyProxy> {
    interface MyView extends View {
        HasSelectHandlers getBackButton();
    }

    @ContentSlot public static final Type<RevealContentHandler<?>> SLOT_Signup = new Type<RevealContentHandler<?>>();

    @NameToken(NameTokens.userCreated)
    @ProxyStandard
    @NoGatekeeper
    public interface MyProxy extends ProxyPlace<UserCreatedPresenter> {}

    private DispatchAsync dispatcher;
    @Inject PlaceManager placeManager;

    @Inject
    public UserCreatedPresenter(EventBus eventBus, MyView view, MyProxy proxy, DispatchAsync dispatcher) {
        super(eventBus, view, proxy, RevealType.Root);
        this.dispatcher = dispatcher;
    }

    @Override
    protected void onBind() {
        registerHandler(getView().getBackButton().addSelectHandler(new SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                PlaceRequest request = new PlaceRequest.Builder().nameToken(NameTokens.login).build();
                placeManager.revealPlace(request);
            }
        }));
    }
}
