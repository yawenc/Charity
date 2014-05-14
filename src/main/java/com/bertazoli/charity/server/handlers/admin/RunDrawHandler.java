package com.bertazoli.charity.server.handlers.admin;

import com.bertazoli.charity.server.businesslogic.DrawBusinessLogic;
import com.bertazoli.charity.shared.action.admin.RunDrawAction;
import com.bertazoli.charity.shared.action.admin.RunDrawResult;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class RunDrawHandler implements ActionHandler<RunDrawAction, RunDrawResult> {
    @Inject private DrawBusinessLogic drawBL;
    
    @Override
    public RunDrawResult execute(RunDrawAction action, ExecutionContext context) throws ActionException {
        return drawBL.runDraw();
    }

    @Override
    public Class<RunDrawAction> getActionType() {
        return RunDrawAction.class;
    }

    @Override
    public void undo(RunDrawAction action, RunDrawResult result, ExecutionContext context) throws ActionException {
    }
}
