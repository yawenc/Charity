package com.bertazoli.charity.client.application.error.transaction;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class TransactionErrorModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(TransactionErrorPresenter.class, TransactionErrorPresenter.MyView.class, TransactionErrorView.class, TransactionErrorPresenter.MyProxy.class);
    }
}
