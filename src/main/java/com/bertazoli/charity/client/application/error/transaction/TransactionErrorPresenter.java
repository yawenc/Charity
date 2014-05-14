package com.bertazoli.charity.client.application.error.transaction;

import com.bertazoli.charity.client.application.ApplicationPresenter;
import com.bertazoli.charity.client.application.gatekeeper.LoggedInGatekeeper;
import com.bertazoli.charity.client.application.security.SecurityManager;
import com.bertazoli.charity.client.place.NameTokens;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

public class TransactionErrorPresenter extends Presenter<TransactionErrorPresenter.MyView, TransactionErrorPresenter.MyProxy> {
    public interface MyView extends View {
    }
    
    @ContentSlot public static final Type<RevealContentHandler<?>> TYPE_SetCharityItem = new Type<RevealContentHandler<?>>();
    
    private SecurityManager security;
    @Inject private DispatchAsync dispatcher;
    @Inject private PlaceManager placeManager;

    @ProxyStandard
    @NameToken(NameTokens.transactionError)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface MyProxy extends ProxyPlace<TransactionErrorPresenter> {}

    @Inject
    public TransactionErrorPresenter(EventBus eventBus, MyView view, MyProxy proxy, SecurityManager security) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);
        this.security = security;
    }
    
    @Override
    public void prepareFromRequest(PlaceRequest request) {
    }
}
