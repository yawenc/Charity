package com.bertazoli.charity.client.application.charity;

import com.bertazoli.charity.client.application.main.MainPresenter;
import com.bertazoli.charity.client.place.NameTokens;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

public class RegisterCharityPresenter extends Presenter<RegisterCharityPresenter.MyView, RegisterCharityPresenter.MyProxy> {
    interface MyView extends View {}

    @NameToken(NameTokens.registerCharity)
    @ProxyStandard
    public interface MyProxy extends ProxyPlace<RegisterCharityPresenter> {}

    @Inject
    public RegisterCharityPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
        super(eventBus, view, proxy, MainPresenter.SLOT_Main);
    }

    protected void onBind() {
        super.onBind();
    }

}
