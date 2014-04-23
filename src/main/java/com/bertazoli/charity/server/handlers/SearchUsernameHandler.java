package com.bertazoli.charity.server.handlers;

import com.bertazoli.charity.server.businesslogic.UserBusinessLogic;
import com.bertazoli.charity.shared.action.SearchUsernameAction;
import com.bertazoli.charity.shared.action.SearchUsernameResult;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class SearchUsernameHandler implements ActionHandler<SearchUsernameAction, SearchUsernameResult> {
    @Inject private UserBusinessLogic userBusinessLogic;

    @Override
    public SearchUsernameResult execute(SearchUsernameAction action, ExecutionContext context) throws ActionException {
        Boolean exists = true;
        exists = userBusinessLogic.searchUsername(action.getUsername());
        return new SearchUsernameResult(exists);
    }

    @Override
    public Class<SearchUsernameAction> getActionType() {
        return null;
    }

    @Override
    public void undo(SearchUsernameAction action, SearchUsernameResult result, ExecutionContext context) throws ActionException {
    }
}
