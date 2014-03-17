package com.bertazoli.charity.client.application.login;

import com.bertazoli.charity.client.place.NameTokens;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

@NoGatekeeper
public class LoginPresenter extends Presenter<LoginPresenter.MyView, LoginPresenter.MyProxy> {
    interface MyView extends View {
    }

    @NameToken(NameTokens.login)
    @ProxyStandard
    public interface MyProxy extends ProxyPlace<LoginPresenter> {
    }

    @Inject
    public LoginPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
        super(eventBus, view, proxy, RevealType.Root);
    }
}
