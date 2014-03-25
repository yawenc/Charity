package com.bertazoli.charity.server.handlers;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.bertazoli.charity.shared.action.LoginAction;
import com.bertazoli.charity.shared.action.LoginResult;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class LoginHandler implements ActionHandler<LoginAction, LoginResult> {
	
	@Inject private ServletContext servletContext;
	@Inject private Provider<HttpServletRequest> requestProvider;
	
	@Override
	public LoginResult execute(LoginAction action, ExecutionContext context)	throws ActionException {
		LoginResult result = null;
		
		return result;
	}

	@Override
	public Class<LoginAction> getActionType() {
		return LoginAction.class;
	}

	@Override
	public void undo(LoginAction action, LoginResult result, ExecutionContext context) throws ActionException {
	}
}
