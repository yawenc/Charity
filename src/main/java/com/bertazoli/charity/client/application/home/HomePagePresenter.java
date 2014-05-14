package com.bertazoli.charity.client.application.home;

import com.bertazoli.charity.client.application.ApplicationPresenter;
import com.bertazoli.charity.client.application.callback.CustomAsyncCallback;
import com.bertazoli.charity.client.application.gatekeeper.LoggedInGatekeeper;
import com.bertazoli.charity.client.application.security.SecurityManager;
import com.bertazoli.charity.client.application.widgets.charity.CharityItemPresenter;
import com.bertazoli.charity.client.place.NameTokens;
import com.bertazoli.charity.shared.action.CharitySearchAction;
import com.bertazoli.charity.shared.action.CharitySearchResult;
import com.bertazoli.charity.shared.beans.Charity;
import com.bertazoli.charity.shared.searchparams.CharitySearchParams;
import com.bertazoli.charity.shared.util.Util;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasText;
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
        void clear();
        HasKeyPressHandlers getCharitySearch();
        HasText getCharitySearchText();
        void clearSelectedCharities();
    }
    
    @ContentSlot public static final Type<RevealContentHandler<?>> TYPE_SetCharityItem = new Type<RevealContentHandler<?>>();
    @ContentSlot public static final Type<RevealContentHandler<?>> TYPE_SetSelectedCharityItem = new Type<RevealContentHandler<?>>();
    
    private SecurityManager security;
    @Inject private DispatchAsync dispatcher;
    @Inject private PlaceManager placeManager;
    @Inject private Provider<CharityItemPresenter> charityItemProvider;
    private Timer timer;
    private boolean running;

    @ProxyStandard
    @NameToken(NameTokens.home)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface MyProxy extends ProxyPlace<HomePagePresenter> {}

    @Inject
    public HomePagePresenter(EventBus eventBus, MyView view, MyProxy proxy, SecurityManager security) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);
        this.security = security;
        timer = new Timer() {
            @Override
            public void run() {
                if (!running) {
                    filterCharities();
                }
            }
        };
    }
    
    @Override
    public void prepareFromRequest(PlaceRequest request) {
        CharitySearchParams params = new CharitySearchParams();
        params.setLimit(10);
        dispatcher.execute(new CharitySearchAction(params), new CustomAsyncCallback<CharitySearchResult>() {
            @Override
            public void onSuccess(CharitySearchResult result) {
                populateSearchResults(result);
            }
        });
        
        params = new CharitySearchParams();
        params.setAllCharitiesCurrentDraw(true);
        dispatcher.execute(new CharitySearchAction(params), new CustomAsyncCallback<CharitySearchResult>() {
            @Override
            public void onSuccess(CharitySearchResult result) {
                populateSelectedResults(result);
            }
        });
    }

    protected void populateSelectedResults(CharitySearchResult result) {
        getView().clearSelectedCharities();
        if (result != null && !Util.isEmpty(result.getCharities())) {
            for (Charity bean : result.getCharities()) {
                CharityItemPresenter item = charityItemProvider.get();
                item.setBean(bean);
                setInSlot(TYPE_SetSelectedCharityItem, item);
            }
        }
    }

    protected void populateSearchResults(CharitySearchResult result) {
        getView().clear();
        if (result != null && !Util.isEmpty(result.getCharities())) {
            for (Charity bean : result.getCharities()) {
                CharityItemPresenter item = charityItemProvider.get();
                item.setBean(bean);
                setInSlot(TYPE_SetCharityItem, item);
            }
        }
    }

    @Override
    protected void onBind() {
        registerHandler(getView().getCharitySearch().addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                timer.schedule(1000);
            }
        }));
    }

    private void filterCharities() {
        running = true;
        CharitySearchParams params = new CharitySearchParams();
        params.setLimit(10);
        params.setText(getView().getCharitySearchText().getText());
        dispatcher.execute(new CharitySearchAction(params), new CustomAsyncCallback<CharitySearchResult>() {
            @Override
            public void onSuccess(CharitySearchResult result) {
                running = false;
                populateSearchResults(result);
            }
        });
    }
}
