package com.bertazoli.charity.server.handlers;

import com.bertazoli.charity.server.businesslogic.CharityBusinessLogic;
import com.bertazoli.charity.shared.action.CharitySearchAction;
import com.bertazoli.charity.shared.action.CharitySearchResult;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class CharitySearchHandler implements ActionHandler<CharitySearchAction, CharitySearchResult> {
    @Inject CharityBusinessLogic charityBL;
    
    @Override
    public CharitySearchResult execute(CharitySearchAction action, ExecutionContext context) throws ActionException {
        return charityBL.search(action.getParams());
    }

    @Override
    public Class<CharitySearchAction> getActionType() {
        return null;
    }

    @Override
    public void undo(CharitySearchAction action, CharitySearchResult result, ExecutionContext context) throws ActionException {
    }
}
