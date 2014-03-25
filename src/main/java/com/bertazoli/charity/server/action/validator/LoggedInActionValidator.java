package com.bertazoli.charity.server.action.validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.rpc.server.actionvalidator.ActionValidator;
import com.gwtplatform.dispatch.rpc.shared.Action;
import com.gwtplatform.dispatch.rpc.shared.Result;
import com.gwtplatform.dispatch.shared.ActionException;

public class LoggedInActionValidator implements ActionValidator {
	
	@Inject private Provider<HttpServletRequest> requestProvider;

	@Override
	public boolean isValid(Action<? extends Result> action) 	throws ActionException {
		boolean result = true;
		HttpSession session = requestProvider.get().getSession();
		Object authenticated = session.getAttribute("login.authenticated");
		
		if (authenticated == null) {
			result = false;
		}
		return result;
	}
}
