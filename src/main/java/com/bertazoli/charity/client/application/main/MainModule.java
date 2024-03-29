package com.bertazoli.charity.client.application.main;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class MainModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(MainPresenter.class, MainPresenter.MyView.class, MainView.class, MainPresenter.MyProxy.class);
    }
}
