package com.bertazoli.charity.client.application.donate;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class DonateModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(DonatePresenter.class, DonatePresenter.MyView.class, DonateView.class, DonatePresenter.MyProxy.class);
    }
}
