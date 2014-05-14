package com.bertazoli.charity.client.application.home;

import com.bertazoli.charity.client.i18n.GlobalDictionary;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.form.TextField;

/**
 * See more on GXT <a href="http://docs.sencha.com/gxt-guides/3/ui/layout/LayoutContainers.html">Layout Containers</a>
 */
public class HomePageView extends ViewImpl implements HomePagePresenter.MyView {
    public interface Binder extends UiBinder<Widget, HomePageView> {
    }

    @UiField FlowLayoutContainer charityList;
    @UiField FlowLayoutContainer selectedCharities;
    @UiField TextField charityName;

    @Inject
    public HomePageView(Binder uiBinder,
            GlobalDictionary dictionary) {
        initWidget(uiBinder.createAndBindUi(this));
    }
    
    @Override
    public void clear() {
        charityList.clear();
    }
    
    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (slot == HomePagePresenter.TYPE_SetCharityItem) {
            charityList.add(content);
        } else if (slot == HomePagePresenter.TYPE_SetSelectedCharityItem) {
            selectedCharities.add(content);
        } else {
            super.setInSlot(slot, content);    
        }
    }

    @Override
    public HasKeyPressHandlers getCharitySearch() {
        return charityName;
    }

    @Override
    public HasText getCharitySearchText() {
        return charityName;
    }

    @Override
    public void clearSelectedCharities() {
        selectedCharities.clear();
    }
}
