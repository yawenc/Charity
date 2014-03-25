package com.bertazoli.charity.server.guice;

import com.bertazoli.charity.server.handlers.LoginHandler;
import com.bertazoli.charity.server.handlers.SignupHandler;
import com.bertazoli.charity.shared.action.LoginAction;
import com.bertazoli.charity.shared.action.SignupAction;
import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;

public class ServerModule extends HandlerModule {
	
    @Override
    protected void configureHandlers() {
    	bindHandler(LoginAction.class, LoginHandler.class); // without validation
    	//bindHandler(LoginAction.class, LoginHandler.class, LoggedInActionValidator.class); // with validation

        bindHandler(SignupAction.class, SignupHandler.class);
    }
}
