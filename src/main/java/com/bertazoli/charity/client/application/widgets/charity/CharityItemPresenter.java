package com.bertazoli.charity.client.application.widgets.charity;

import com.bertazoli.charity.client.place.NameTokens;
import com.bertazoli.charity.client.place.TokenParameters;
import com.bertazoli.charity.shared.beans.Charity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

public class CharityItemPresenter extends PresenterWidget<CharityItemPresenter.MyView> {

    public interface MyView extends View {
        void setBean(Charity bean);
        Long getCharityId();
        HasClickHandlers getMainPanel();
    }
    
    @Inject PlaceManager placeManager;
    
    @Inject
    public CharityItemPresenter(EventBus eventBus, MyView view) {
        super(eventBus, view);
    }
    
    @Override
    protected void onBind() {
        registerHandler(getView().getMainPanel().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                donateToCharity(getView().getCharityId());
            }
        }));
    }

    private void donateToCharity(Long charityId) {
        PlaceRequest request = new PlaceRequest.Builder().nameToken(NameTokens.getDonate()).with(TokenParameters.CHARITYID, String.valueOf(charityId)).build();
        placeManager.revealPlace(request);
    }

    public void setBean(Charity bean) {
        getView().setBean(bean);
    }
}
