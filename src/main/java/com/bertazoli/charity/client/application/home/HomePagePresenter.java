package com.bertazoli.charity.client.application.home;

import com.bertazoli.charity.client.application.ApplicationPresenter;
import com.bertazoli.charity.client.application.callback.CustomAsyncCallback;
import com.bertazoli.charity.client.application.events.login.LoginAuthenticatedEvent;
import com.bertazoli.charity.client.application.gatekeeper.LoggedInGatekeeper;
import com.bertazoli.charity.client.application.security.SecurityManager;
import com.bertazoli.charity.client.application.widgets.charity.CharityItemPresenter;
import com.bertazoli.charity.client.place.NameTokens;
import com.bertazoli.charity.shared.action.CharitySearchAction;
import com.bertazoli.charity.shared.action.CharitySearchResult;
import com.bertazoli.charity.shared.action.LogoutAction;
import com.bertazoli.charity.shared.action.LogoutResult;
import com.bertazoli.charity.shared.beans.Charity;
import com.bertazoli.charity.shared.searchparams.CharitySearchParams;
import com.bertazoli.charity.shared.util.Util;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.inject.Provider;
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

public class HomePagePresenter extends Presenter<HomePagePresenter.MyView, HomePagePresenter.MyProxy> {
    public interface MyView extends View {
        void switchView(boolean loggedIn);
        HasClickHandlers getLogoutButton();
        void clear();
    }
    
    @ContentSlot public static final Type<RevealContentHandler<?>> TYPE_SetCharityItem = new Type<RevealContentHandler<?>>();
    
    private SecurityManager security;
    @Inject private DispatchAsync dispatcher;
    @Inject private PlaceManager placeManager;
    @Inject private Provider<CharityItemPresenter> charityItemProvider;

    @ProxyStandard
    @NameToken(NameTokens.home)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface MyProxy extends ProxyPlace<HomePagePresenter> {
    }

    @Inject
    public HomePagePresenter(EventBus eventBus, MyView view, MyProxy proxy, SecurityManager security) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);
        this.security = security;
    }
    
    @Override
    public void prepareFromRequest(PlaceRequest request) {
        CharitySearchParams params = new CharitySearchParams();
        params.setLimit(10);
        dispatcher.execute(new CharitySearchAction(params), new CustomAsyncCallback<CharitySearchResult>() {
            @Override
            public void onSuccess(CharitySearchResult result) {
                getView().clear();
                if (result != null && !Util.isEmpty(result.getCharities())) {
                    for (Charity bean : result.getCharities()) {
                        CharityItemPresenter item = charityItemProvider.get();
                        item.setBean(bean);
                        setInSlot(TYPE_SetCharityItem, item);
                    }
                }
            }
        });
    }

    @Override
    protected void onBind() {
        getView().switchView(security.getUser().isLoggedIn());
        
        getView().getLogoutButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                dispatcher.execute(new LogoutAction(), new CustomAsyncCallback<LogoutResult>() {
                    @Override
                    public void onSuccess(LogoutResult result) {
                        getEventBus().fireEvent(new LoginAuthenticatedEvent(null,null));
                        PlaceRequest request = new PlaceRequest.Builder().nameToken(NameTokens.getHome()).build();
                        placeManager.revealPlace(request);
                    }
                });
            }
        });
    }
}
