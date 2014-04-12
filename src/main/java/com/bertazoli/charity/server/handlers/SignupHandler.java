package com.bertazoli.charity.server.handlers;

import javax.servlet.http.HttpServletRequest;

import com.bertazoli.charity.server.businesslogic.UserBusinessLogic;
import com.bertazoli.charity.shared.action.SignupAction;
import com.bertazoli.charity.shared.action.SignupResult;
import com.bertazoli.charity.shared.beans.User;
import com.bertazoli.charity.shared.exceptions.ValidationException;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class SignupHandler implements ActionHandler<SignupAction, SignupResult> {
	
	@Inject private Provider<HttpServletRequest> requestProvider;
    @Inject private UserBusinessLogic userBusinessLogic;
	
	@Override
    public SignupResult execute(SignupAction action, ExecutionContext context) throws ActionException {
        SignupResult result = null;
        User user;
        try {
            user = userBusinessLogic.create(action.getUserIn());
        } catch (ValidationException e) {
            throw new ActionException(e.getMessage());
        }
        if (user != null) {
            result = new SignupResult(user);
        }
		return result;
	}

	@Override
    public Class<SignupAction> getActionType() {
        return SignupAction.class;
	}

	@Override
    public void undo(SignupAction action, SignupResult result, ExecutionContext context) throws ActionException {
	}
}
