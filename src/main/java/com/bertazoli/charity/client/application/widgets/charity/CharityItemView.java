package com.bertazoli.charity.client.application.widgets.charity;

import com.bertazoli.charity.shared.beans.Charity;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class CharityItemView extends ViewImpl implements CharityItemPresenter.MyView {
    public interface Binder extends UiBinder<Widget, CharityItemView> {
    }
    
    @UiField Label name;
    @UiField FocusPanel mainPanel;
    private Charity bean;
    
    @Inject
    public CharityItemView(Binder binder) {
        initWidget(binder.createAndBindUi(this));
    }

    @Override
    public void setBean(Charity bean) {
        this.bean = bean;
        name.setText(bean.getName());
    }

    @Override
    public Long getCharityId() {
        return bean.getId();
    }

    @Override
    public HasClickHandlers getMainPanel() {
        return mainPanel;
    }
}
