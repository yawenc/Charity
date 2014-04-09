package com.bertazoli.charity.client.application.home;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * See more on GXT <a href="http://docs.sencha.com/gxt-guides/3/ui/layout/LayoutContainers.html">Layout Containers</a>
 */
public class HomePageView extends ViewImpl implements HomePagePresenter.MyView {
    public interface Binder extends UiBinder<Widget, HomePageView> {
    }

    @UiField Hyperlink login;
    @UiField Hyperlink logout;
    @UiField HTMLPanel main;

    @Inject
    public HomePageView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void switchView(boolean loggedIn) {
        login.setVisible(!loggedIn);
        logout.setVisible(loggedIn);
    }

    @Override
    public HasClickHandlers getLogoutButton() {
        return logout;
    }
}
