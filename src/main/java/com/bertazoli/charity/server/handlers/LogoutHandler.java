package com.bertazoli.charity.server.handlers;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bertazoli.charity.shared.action.LogoutAction;
import com.bertazoli.charity.shared.action.LogoutResult;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class LogoutHandler implements ActionHandler<LogoutAction, LogoutResult> {
    @Inject private ServletContext servletContext;
    @Inject private Provider<HttpServletRequest> requestProvider;

    @Override
    public LogoutResult execute(LogoutAction action, ExecutionContext context) throws ActionException {
        LogoutResult result = new LogoutResult();
        HttpSession session = requestProvider.get().getSession();
        session.invalidate();
        return result;
    }

    @Override
    public Class<LogoutAction> getActionType() {
        return LogoutAction.class;
    }

    @Override
    public void undo(LogoutAction action, LogoutResult result, ExecutionContext context) throws ActionException {
    }
}
