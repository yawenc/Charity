package com.bertazoli.charity.client.application.signup;

import com.bertazoli.charity.client.application.callback.CustomAsyncCallback;
import com.bertazoli.charity.client.place.NameTokens;
import com.bertazoli.charity.shared.action.SearchUsernameAction;
import com.bertazoli.charity.shared.action.SearchUsernameResult;
import com.bertazoli.charity.shared.action.SignupAction;
import com.bertazoli.charity.shared.action.SignupResult;
import com.bertazoli.charity.shared.beans.User;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasText;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.HasSelectHandlers;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class SignupPresenter extends Presenter<SignupPresenter.MyView, SignupPresenter.MyProxy> {
    interface MyView extends View {
        HasSelectHandlers getSendButton();
        boolean validate();
        User mapBean(User user);
        HasKeyUpHandlers getUsernameHandler();
        HasText getUsername();
        void addUsernameValidationError(Boolean exists);
    }

    @ContentSlot public static final Type<RevealContentHandler<?>> SLOT_Signup = new Type<RevealContentHandler<?>>();

    @NameToken(NameTokens.signup)
    @ProxyStandard
    @NoGatekeeper
    public interface MyProxy extends ProxyPlace<SignupPresenter> {}

    private DispatchAsync dispatcher;
    @Inject PlaceManager placeManager;
    
    private Timer timer;
    private boolean running;

    @Inject
    public SignupPresenter(EventBus eventBus, MyView view, MyProxy proxy, DispatchAsync dispatcher) {
        super(eventBus, view, proxy, RevealType.Root);
        this.dispatcher = dispatcher;
        
        timer = new Timer() {
            @Override
            public void run() {
                if (!running) {
                    searchUsername();
                }
            }
        };
    }

    @Override
    protected void onBind() {
        registerHandler(getView().getSendButton().addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                if (getView().validate()) {
                    registerUser();
                }
            }
        }));
        
        registerHandler(getView().getUsernameHandler().addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                timer.schedule(2000);
            }
        }));
    }
    
    private void searchUsername() {
        running = true;
        dispatcher.execute(new SearchUsernameAction(getView().getUsername().getText()), new CustomAsyncCallback<SearchUsernameResult>() {
            @Override
            public void onSuccess(SearchUsernameResult result) {
                running = false;
                getView().addUsernameValidationError(result.getExists());
            }
        });
    }

    private void registerUser() {
        User user = getView().mapBean(new User());
        dispatcher.execute(new SignupAction(user), new CustomAsyncCallback<SignupResult>() {
            @Override
            public void onSuccess(SignupResult result) {
                if (result != null) {
                    PlaceRequest request = new PlaceRequest.Builder().nameToken(NameTokens.home).build();
                    placeManager.revealPlace(request);
                }
            }
        });
    }
}
