package com.bertazoli.charity.client.application;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.widget.core.client.container.Viewport;

/**
 * See more on GXT <a href="http://docs.sencha.com/gxt-guides/3/ui/layout/LayoutContainers.html">Layout Containers</a>
 */
public class ApplicationView extends ViewImpl implements ApplicationPresenter.MyView {
    public interface Binder extends UiBinder<Widget, ApplicationView> {
    }

    @UiField SimplePanel header;
    @UiField Viewport main;
    @UiField SimplePanel footer;
    
    @Inject
    public ApplicationView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (slot == ApplicationPresenter.TYPE_SetMainContent) {
            main.setWidget(content);
        } else if (slot == ApplicationPresenter.TYPE_SetHeaderContent) {
            header.setWidget(content);
        } else if (slot == ApplicationPresenter.TYPE_SetFooterContent) {
            footer.setWidget(content);
        } else {
            super.setInSlot(slot, content);
        }
    }
}
