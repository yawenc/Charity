package com.bertazoli.charity.client.application.error.transaction;

import com.bertazoli.charity.client.i18n.GlobalDictionary;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * See more on GXT <a href="http://docs.sencha.com/gxt-guides/3/ui/layout/LayoutContainers.html">Layout Containers</a>
 */
public class TransactionErrorView extends ViewImpl implements TransactionErrorPresenter.MyView {
    public interface Binder extends UiBinder<Widget, TransactionErrorView> {
    }

    @Inject
    public TransactionErrorView(Binder uiBinder,
            GlobalDictionary dictionary) {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
