package com.bertazoli.charity.client.application.error.general;

import com.bertazoli.charity.client.i18n.GlobalDictionary;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * See more on GXT <a href="http://docs.sencha.com/gxt-guides/3/ui/layout/LayoutContainers.html">Layout Containers</a>
 */
public class ErrorView extends ViewImpl implements ErrorPresenter.MyView {
    public interface Binder extends UiBinder<Widget, ErrorView> {
    }

    @Inject
    public ErrorView(Binder uiBinder,
            GlobalDictionary dictionary) {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
