package com.bertazoli.charity.client.application.header;

import com.bertazoli.charity.client.i18n.GlobalDictionary;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent.HasSelectHandlers;

public class HeaderView extends ViewImpl implements HeaderPresenter.MyView {
    public interface Binder extends UiBinder<Widget, HeaderView> {
    }

    @UiField TextButton logout;
    @UiField TextButton userSettings;
    @UiField Label totalDonated;
    @Inject GlobalDictionary dictionary;

    @Inject
    public HeaderView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setTotalDonation(Double total) {
        totalDonated.setText(dictionary.totalThatHaveBeenDonated(NumberFormat.getFormat(dictionary.currencyFormat()).format(total)));
    }

    @Override
    public HasSelectHandlers getLogoutButton() {
        return logout;
    }

    @Override
    public HasSelectHandlers getUserSettingsButton() {
        return userSettings;
    }
}
