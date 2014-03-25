package com.bertazoli.charity.server.guice;

import com.bertazoli.charity.server.action.validator.LoggedInActionValidator;
import com.bertazoli.charity.server.handlers.LoginHandler;
import com.bertazoli.charity.shared.action.LoginAction;
import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;

public class ServerModule extends HandlerModule {
	
    @Override
    protected void configureHandlers() {
    	bindHandler(LoginAction.class, LoginHandler.class, LoggedInActionValidator.class);
    }
}
