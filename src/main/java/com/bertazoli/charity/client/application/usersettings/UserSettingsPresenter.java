package com.bertazoli.charity.client.application.usersettings;

import com.bertazoli.charity.client.application.core.CustomPresenter;
import com.bertazoli.charity.client.application.gatekeeper.LoggedInGatekeeper;
import com.bertazoli.charity.client.application.main.MainPresenter;
import com.bertazoli.charity.client.place.NameTokens;
import com.bertazoli.charity.shared.action.user.SearchMyDonationsAction;
import com.bertazoli.charity.shared.action.user.SearchMyDonationsResult;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

public class UserSettingsPresenter extends CustomPresenter<UserSettingsPresenter.MyView, UserSettingsPresenter.MyProxy> {
    interface MyView extends View {
    }

    @ContentSlot
    public static final Type<RevealContentHandler<?>> SLOT_UserSettings = new Type<RevealContentHandler<?>>();

    @NameToken(NameTokens.userSettings)
    @ProxyStandard
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface MyProxy extends ProxyPlace<UserSettingsPresenter> {
    }

    @Inject
    public UserSettingsPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
        super(eventBus, view, proxy, MainPresenter.SLOT_Main);
    }
    
    @Override
    public void prepareFromRequest(PlaceRequest request) {
        searchMyDonations();
    }

    private void searchMyDonations() {
        dispatcher.execute(new SearchMyDonationsAction(), new AsyncCallback<SearchMyDonationsResult>() {

            @Override
            public void onFailure(Throwable caught) {
                caught.printStackTrace();
            }

            @Override
            public void onSuccess(SearchMyDonationsResult result) {
                System.out.println(result);
            }
        });
    }
}
