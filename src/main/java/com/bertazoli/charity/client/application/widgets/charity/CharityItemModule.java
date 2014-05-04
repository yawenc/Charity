package com.bertazoli.charity.client.application.widgets.charity;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class CharityItemModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        bindPresenterWidget(CharityItemPresenter.class, CharityItemPresenter.MyView.class, CharityItemView.class);
    }
}
