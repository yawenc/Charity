package com.bertazoli.charity.client.application.login;

import com.bertazoli.charity.client.place.NameTokens;
import com.bertazoli.charity.shared.action.LoginAction;
import com.bertazoli.charity.shared.action.LoginResult;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

@NoGatekeeper
public class LoginPresenter extends Presenter<LoginPresenter.MyView, LoginPresenter.MyProxy> {
    interface MyView extends View {
		HasClickHandlers getSubmitButton();
		boolean validate();
		String getPassword();
		String getUsername();
    }

    @NameToken(NameTokens.login)
    @ProxyStandard
    public interface MyProxy extends ProxyPlace<LoginPresenter> {}
    
    private DispatchAsync dispatcher;

    @Inject
    public LoginPresenter(EventBus eventBus, MyView view, MyProxy proxy, DispatchAsync dispatcher) {
        super(eventBus, view, proxy, RevealType.Root);
        this.dispatcher = dispatcher;
    }
    
    @Override
    protected void onBind() {
    	super.onBind();
    	
    	getView().getSubmitButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (getView().validate()) {
					doLogin(getView().getUsername(), getView().getPassword());
				}
			}
		});
    }

	private void doLogin(String username, String password) {
		dispatcher.execute(new LoginAction(username, password), new AsyncCallback<LoginResult>() {
			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(LoginResult result) {
			}
		});
	}
}
