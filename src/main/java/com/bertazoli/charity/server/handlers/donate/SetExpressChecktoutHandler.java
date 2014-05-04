package com.bertazoli.charity.server.handlers.donate;

import com.bertazoli.charity.server.businesslogic.DonateBusinessLogic;
import com.bertazoli.charity.shared.action.donate.SetExpressChecktoutAction;
import com.bertazoli.charity.shared.action.donate.SetExpressChecktoutResult;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class SetExpressChecktoutHandler implements ActionHandler<SetExpressChecktoutAction, SetExpressChecktoutResult> {
    @Inject DonateBusinessLogic donateBL;

    @Override
    public SetExpressChecktoutResult execute(SetExpressChecktoutAction action, ExecutionContext context) throws ActionException {
        return new SetExpressChecktoutResult(donateBL.setExpressChecktout(action.getInput()));
    }

    @Override
    public Class<SetExpressChecktoutAction> getActionType() {
        return SetExpressChecktoutAction.class;
    }

    @Override
    public void undo(SetExpressChecktoutAction action, SetExpressChecktoutResult result, ExecutionContext context) throws ActionException {
    }
}
