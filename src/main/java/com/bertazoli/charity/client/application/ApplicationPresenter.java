package com.bertazoli.charity.client.application;

import com.bertazoli.charity.client.application.core.CustomPresenter;
import com.bertazoli.charity.client.application.header.HeaderPresenter;
import com.bertazoli.charity.shared.action.donate.FetchTotalDonationsAction;
import com.bertazoli.charity.shared.action.donate.FetchTotalDonationsResult;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class ApplicationPresenter extends CustomPresenter<ApplicationPresenter.MyView, ApplicationPresenter.MyProxy> {
    public interface MyView extends View {
    }

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetHeaderContent = new Type<RevealContentHandler<?>>();
    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetMainContent = new Type<RevealContentHandler<?>>();
    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetFooterContent = new Type<RevealContentHandler<?>>();
    
    private HeaderPresenter header;
    private Timer retrieveDonations;
    private int retrieveTime = 1000*60;

    @ProxyStandard
    public interface MyProxy extends Proxy<ApplicationPresenter> {
    }

    @Inject
    public ApplicationPresenter(EventBus eventBus, MyView view, MyProxy proxy, HeaderPresenter header) {
        super(eventBus, view, proxy, RevealType.Root);
        this.header = header;
        
        retrieveDonations = new Timer() {
            @Override
            public void run() {
                retrieveDonations();
            }
        };
    }
    
    private void retrieveDonations() {
        dispatcher.execute(new FetchTotalDonationsAction(), new AsyncCallback<FetchTotalDonationsResult>() {
            @Override
            public void onFailure(Throwable caught) {
                retrieveDonations.schedule(retrieveTime);
            }

            @Override
            public void onSuccess(FetchTotalDonationsResult result) {
                if (result != null) {
                    header.setTotalDonation(result.getOutput());    
                }
                retrieveDonations.schedule(retrieveTime);
            }
        });
    }
    
    @Override
    protected void onReveal() {
        super.onReveal();
        retrieveDonations();
    }

    @Override
    protected void onBind() {
        setInSlot(TYPE_SetHeaderContent, header);
    }
}
