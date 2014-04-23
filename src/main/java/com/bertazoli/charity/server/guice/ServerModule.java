package com.bertazoli.charity.server.guice;

import com.bertazoli.charity.server.handlers.LoginHandler;
import com.bertazoli.charity.server.handlers.LogoutHandler;
import com.bertazoli.charity.server.handlers.OracleHandler;
import com.bertazoli.charity.server.handlers.SearchUsernameHandler;
import com.bertazoli.charity.server.handlers.SignupHandler;
import com.bertazoli.charity.shared.action.LoginAction;
import com.bertazoli.charity.shared.action.LogoutAction;
import com.bertazoli.charity.shared.action.OracleAction;
import com.bertazoli.charity.shared.action.SearchUsernameAction;
import com.bertazoli.charity.shared.action.SignupAction;
import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;

public class ServerModule extends HandlerModule {
	
    @Override
    protected void configureHandlers() {
    	bindHandler(LoginAction.class, LoginHandler.class); // without validation
        bindHandler(LogoutAction.class, LogoutHandler.class);
        bindHandler(OracleAction.class, OracleHandler.class);
        bindHandler(SearchUsernameAction.class, SearchUsernameHandler.class);
    	//bindHandler(LoginAction.class, LoginHandler.class, LoggedInActionValidator.class); // with validation

        bindHandler(SignupAction.class, SignupHandler.class);
    }
}
