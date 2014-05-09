package com.bertazoli.charity.client.application.donate;

import com.bertazoli.charity.client.application.callback.CustomAsyncCallback;
import com.bertazoli.charity.client.application.core.CustomPresenter;
import com.bertazoli.charity.client.application.gatekeeper.LoggedInGatekeeper;
import com.bertazoli.charity.client.application.main.MainPresenter;
import com.bertazoli.charity.client.place.NameTokens;
import com.bertazoli.charity.client.place.TokenParameters;
import com.bertazoli.charity.shared.action.CharitySearchAction;
import com.bertazoli.charity.shared.action.CharitySearchResult;
import com.bertazoli.charity.shared.action.donate.SetExpressChecktoutAction;
import com.bertazoli.charity.shared.action.donate.SetExpressChecktoutResult;
import com.bertazoli.charity.shared.beans.Charity;
import com.bertazoli.charity.shared.beans.DonationInformation;
import com.bertazoli.charity.shared.searchparams.CharitySearchParams;
import com.bertazoli.charity.shared.util.Util;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.HasSelectHandlers;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class DonatePresenter extends CustomPresenter<DonatePresenter.MyView, DonatePresenter.MyProxy> {
    interface MyView extends View {
        HasValueChangeHandlers<Integer> getSliderValueChange();
        void setTextPercentage(Integer percentage);
        HasValueChangeHandlers<Integer> getPercentageValueChange();
        void setSliderPercentage(Integer percentage);
        void setBean(Charity bean);
        HasSelectHandlers getSubmit();
        boolean validate();
        DonationInformation mapBean(DonationInformation bean);
    }

    @NameToken(NameTokens.donate)
    @ProxyStandard
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface MyProxy extends ProxyPlace<DonatePresenter> {}

    @Inject
    public DonatePresenter(EventBus eventBus, MyView view, MyProxy proxy) {
        super(eventBus, view, proxy, MainPresenter.SLOT_Main);
    }

    protected void onBind() {
        registerHandler(getView().getSliderValueChange().addValueChangeHandler(new ValueChangeHandler<Integer>() {
            @Override
            public void onValueChange(ValueChangeEvent<Integer> event) {
                if (event != null)
                    getView().setTextPercentage(event.getValue());
            }
        }));
        
        registerHandler(getView().getPercentageValueChange().addValueChangeHandler(new ValueChangeHandler<Integer>() {
            @Override
            public void onValueChange(ValueChangeEvent<Integer> event) {
                if (event != null)
                    getView().setSliderPercentage(event.getValue());
            }
        }));
        
        registerHandler(getView().getSubmit().addSelectHandler(new SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                if (getView().validate()) {
                    donate();
                }
            }
        }));
    }
    
    private void donate() {
        DonationInformation bean = getView().mapBean(new DonationInformation());
        dispatcher.execute(new SetExpressChecktoutAction(bean), new AsyncCallback<SetExpressChecktoutResult>() {
            @Override
            public void onFailure(Throwable caught) {
                
            }

            @Override
            public void onSuccess(SetExpressChecktoutResult result) {
                if (result != null && result.getOutput() != null) {
                    Window.Location.assign(result.getOutput());
                }
            }
        });
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        Long charityId = Long.parseLong(request.getParameter(TokenParameters.CHARITYID, "-1"));

        if (charityId > 0) {
            CharitySearchParams params = new CharitySearchParams();
            params.setCharityId(charityId);
            dispatcher.execute(new CharitySearchAction(params), new CustomAsyncCallback<CharitySearchResult>() {
                @Override
                public void onSuccess(CharitySearchResult result) {
                    if (!Util.isEmpty(result.getCharities())) {
                        getView().setBean(result.getCharities().get(0));    
                    }
                }
            });
        } else {
            // give the user the chance to search for a charity
        }
    }
}
