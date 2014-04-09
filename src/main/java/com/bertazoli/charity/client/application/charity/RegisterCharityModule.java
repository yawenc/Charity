package com.bertazoli.charity.client.application.charity;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class RegisterCharityModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(RegisterCharityPresenter.class, RegisterCharityPresenter.MyView.class, RegisterCharityView.class, RegisterCharityPresenter.MyProxy.class);
    }
}
