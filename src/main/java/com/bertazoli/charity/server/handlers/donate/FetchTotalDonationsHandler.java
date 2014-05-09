package com.bertazoli.charity.server.handlers.donate;

import com.bertazoli.charity.server.businesslogic.DonateBusinessLogic;
import com.bertazoli.charity.shared.action.donate.FetchTotalDonationsAction;
import com.bertazoli.charity.shared.action.donate.FetchTotalDonationsResult;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class FetchTotalDonationsHandler implements ActionHandler<FetchTotalDonationsAction, FetchTotalDonationsResult> {
    @Inject DonateBusinessLogic donateBL;

    @Override
    public FetchTotalDonationsResult execute(FetchTotalDonationsAction action, ExecutionContext context) throws ActionException {
        return new FetchTotalDonationsResult(donateBL.fetchTotalDonations());
    }

    @Override
    public Class<FetchTotalDonationsAction> getActionType() {
        return null;
    }

    @Override
    public void undo(FetchTotalDonationsAction action, FetchTotalDonationsResult result, ExecutionContext context) throws ActionException {
    }
}
