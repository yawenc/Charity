package com.bertazoli.charity.client.application.core;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public abstract class CustomPresenter<V extends View, Proxy_ extends Proxy<?>> extends Presenter<V, Proxy_> {
    @Inject public DispatchAsync dispatcher;
    @Inject public PlaceManager placeManager;
    
    public CustomPresenter(EventBus eventBus, V view, Proxy_ proxy, Type<RevealContentHandler<?>> slotMain) {
        super(eventBus, view, proxy, slotMain);
    }
    
    public CustomPresenter(EventBus eventBus, V view, Proxy_ proxy, RevealType revealType) {
        super(eventBus, view, proxy, revealType);
    }
}
