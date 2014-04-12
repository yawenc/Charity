package com.bertazoli.charity.server.handlers;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.bertazoli.charity.server.businesslogic.OracleBusinessLogic;
import com.bertazoli.charity.shared.action.OracleAction;
import com.bertazoli.charity.shared.action.OracleResult;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class OracleHandler implements ActionHandler<OracleAction, OracleResult> {
	
	@Inject private ServletContext servletContext;
	@Inject private Provider<HttpServletRequest> requestProvider;
    @Inject private OracleBusinessLogic userBusinessLogic;

    @Override
    public OracleResult execute(OracleAction action, ExecutionContext context) throws ActionException {
        return new OracleResult(userBusinessLogic.search(action.getParams()));
    }
    @Override
    public Class<OracleAction> getActionType() {
        return OracleAction.class;
    }
    @Override
    public void undo(OracleAction action, OracleResult result, ExecutionContext context) throws ActionException {
    }
	
}
