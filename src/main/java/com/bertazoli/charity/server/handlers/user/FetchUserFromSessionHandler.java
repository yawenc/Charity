package com.bertazoli.charity.server.handlers.user;

import com.bertazoli.charity.server.businesslogic.UserBusinessLogic;
import com.bertazoli.charity.shared.action.user.FetchUserFromSessionAction;
import com.bertazoli.charity.shared.action.user.FetchUserFromSessionResult;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class FetchUserFromSessionHandler implements ActionHandler<FetchUserFromSessionAction, FetchUserFromSessionResult> {
    
    @Inject private UserBusinessLogic userBusinessLogic;

    @Override
    public FetchUserFromSessionResult execute(FetchUserFromSessionAction action, ExecutionContext context) throws ActionException {
        return new FetchUserFromSessionResult(userBusinessLogic.fetchFromSession(action.getSessionID()));
    }

    @Override
    public Class<FetchUserFromSessionAction> getActionType() {
        return FetchUserFromSessionAction.class;
    }

    @Override
    public void undo(FetchUserFromSessionAction action, FetchUserFromSessionResult result, ExecutionContext context) throws ActionException {
    }

}
