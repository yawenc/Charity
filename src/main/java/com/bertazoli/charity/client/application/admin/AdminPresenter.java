package com.bertazoli.charity.client.application.admin;

import com.bertazoli.charity.client.application.callback.CustomAsyncCallback;
import com.bertazoli.charity.client.application.core.CustomPresenter;
import com.bertazoli.charity.client.application.gatekeeper.AdminGateKeeper;
import com.bertazoli.charity.client.application.main.MainPresenter;
import com.bertazoli.charity.client.place.NameTokens;
import com.bertazoli.charity.shared.action.admin.RunDrawAction;
import com.bertazoli.charity.shared.action.admin.RunDrawResult;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.HasSelectHandlers;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class AdminPresenter extends CustomPresenter<AdminPresenter.MyView, AdminPresenter.MyProxy> {
    interface MyView extends View {
        HasSelectHandlers getRunDraw();
    }

    @NameToken(NameTokens.admin)
    @ProxyStandard
    @UseGatekeeper(AdminGateKeeper.class)
    public interface MyProxy extends ProxyPlace<AdminPresenter> {}

    @Inject
    public AdminPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
        super(eventBus, view, proxy, MainPresenter.SLOT_Main);
    }

    @Override
    protected void onBind() {
        registerHandler(getView().getRunDraw().addSelectHandler(new SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                dispatcher.execute(new RunDrawAction(), new CustomAsyncCallback<RunDrawResult>() {
                    @Override
                    public void onSuccess(RunDrawResult result) {
                        
                    }
                });
            }
        }));
    }
}
