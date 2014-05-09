package com.bertazoli.charity.client.application.header;

import com.bertazoli.charity.client.application.security.SecurityManager;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

public class HeaderPresenter extends PresenterWidget<HeaderPresenter.MyView> {
    public interface MyView extends View {
        void setTotalDonation(Double total);
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
}
