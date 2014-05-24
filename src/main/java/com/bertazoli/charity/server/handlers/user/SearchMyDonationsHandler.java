package com.bertazoli.charity.server.handlers.user;

import com.bertazoli.charity.server.businesslogic.DonateBusinessLogic;
import com.bertazoli.charity.shared.action.user.SearchMyDonationsAction;
import com.bertazoli.charity.shared.action.user.SearchMyDonationsResult;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class SearchMyDonationsHandler implements ActionHandler<SearchMyDonationsAction, SearchMyDonationsResult> {
    @Inject private DonateBusinessLogic donateBL;

    @Override
    public SearchMyDonationsResult execute(SearchMyDonationsAction action, ExecutionContext context) throws ActionException {
        return new SearchMyDonationsResult(donateBL.searchMyDonations());
    }

    @Override
    public Class<SearchMyDonationsAction> getActionType() {
        return SearchMyDonationsAction.class;
    }

    @Override
    public void undo(SearchMyDonationsAction action, SearchMyDonationsResult result, ExecutionContext context) throws ActionException {
    }
}
