package com.bertazoli.charity.client.application.home;

import com.bertazoli.charity.client.application.ApplicationPresenter;
import com.bertazoli.charity.client.application.callback.CustomAsyncCallback;
import com.bertazoli.charity.client.application.events.login.LoginAuthenticatedEvent;
import com.bertazoli.charity.client.application.gatekeeper.LoggedInGatekeeper;
import com.bertazoli.charity.client.application.security.SecurityManager;
import com.bertazoli.charity.client.place.NameTokens;
import com.bertazoli.charity.shared.action.LogoutAction;
import com.bertazoli.charity.shared.action.LogoutResult;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

public class HomePagePresenter extends Presenter<HomePagePresenter.MyView, HomePagePresenter.MyProxy> {
    public interface MyView extends View {
        void switchView(boolean loggedIn);
        HasClickHandlers getLogoutButton();
    }

    private SecurityManager security;
    @Inject private DispatchAsync dispatcher;
    @Inject private PlaceManager placeManager;

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
    protected void onBind() {
        getView().switchView(security.getUser().isLoggedIn());
        
        getView().getLogoutButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                dispatcher.execute(new LogoutAction(), new CustomAsyncCallback<LogoutResult>() {
                    @Override
                    public void onSuccess(LogoutResult result) {
                        getEventBus().fireEvent(new LoginAuthenticatedEvent(null));
                        PlaceRequest request = new PlaceRequest.Builder().nameToken(NameTokens.getHome()).build();
                        placeManager.revealPlace(request);
                    }
                });
            }
        });
    }
}
