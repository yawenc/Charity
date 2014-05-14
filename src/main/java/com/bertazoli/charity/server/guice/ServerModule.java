package com.bertazoli.charity.server.guice;

import com.bertazoli.charity.server.action.validator.AdminActionValidator;
import com.bertazoli.charity.server.action.validator.LoggedInActionValidator;
import com.bertazoli.charity.server.handlers.CharitySearchHandler;
import com.bertazoli.charity.server.handlers.LoginHandler;
import com.bertazoli.charity.server.handlers.LogoutHandler;
import com.bertazoli.charity.server.handlers.OracleHandler;
import com.bertazoli.charity.server.handlers.SearchUsernameHandler;
import com.bertazoli.charity.server.handlers.SignupHandler;
import com.bertazoli.charity.server.handlers.admin.RunDrawHandler;
import com.bertazoli.charity.server.handlers.donate.FetchTotalDonationsHandler;
import com.bertazoli.charity.server.handlers.donate.SetExpressChecktoutHandler;
import com.bertazoli.charity.server.handlers.user.FetchUserFromSessionHandler;
import com.bertazoli.charity.shared.action.CharitySearchAction;
import com.bertazoli.charity.shared.action.LoginAction;
import com.bertazoli.charity.shared.action.LogoutAction;
import com.bertazoli.charity.shared.action.OracleAction;
import com.bertazoli.charity.shared.action.SearchUsernameAction;
import com.bertazoli.charity.shared.action.SignupAction;
import com.bertazoli.charity.shared.action.admin.RunDrawAction;
import com.bertazoli.charity.shared.action.donate.FetchTotalDonationsAction;
import com.bertazoli.charity.shared.action.donate.SetExpressChecktoutAction;
import com.bertazoli.charity.shared.action.user.FetchUserFromSessionAction;
import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;

public class ServerModule extends HandlerModule {
	
    @Override
    protected void configureHandlers() {
        bindHandler(SignupAction.class, SignupHandler.class);
    	bindHandler(LoginAction.class, LoginHandler.class);
        bindHandler(LogoutAction.class, LogoutHandler.class);
        bindHandler(OracleAction.class, OracleHandler.class);
        bindHandler(SearchUsernameAction.class, SearchUsernameHandler.class);
        bindHandler(FetchUserFromSessionAction.class, FetchUserFromSessionHandler.class);
        bindHandler(FetchTotalDonationsAction.class, FetchTotalDonationsHandler.class);
        bindHandler(CharitySearchAction.class, CharitySearchHandler.class, LoggedInActionValidator.class);
        bindHandler(SetExpressChecktoutAction.class, SetExpressChecktoutHandler.class, LoggedInActionValidator.class);
        bindHandler(RunDrawAction.class, RunDrawHandler.class, AdminActionValidator.class);
    }
}
