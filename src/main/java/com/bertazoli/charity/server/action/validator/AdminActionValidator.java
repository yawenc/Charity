package com.bertazoli.charity.server.action.validator;

import javax.servlet.http.HttpSession;

import com.bertazoli.charity.server.businesslogic.UserBusinessLogic;
import com.bertazoli.charity.shared.beans.User;
import com.bertazoli.charity.shared.beans.enums.UserRole;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.shared.Action;
import com.gwtplatform.dispatch.rpc.shared.Result;
import com.gwtplatform.dispatch.shared.ActionException;

public class AdminActionValidator extends LoggedInActionValidator {
    @Inject private UserBusinessLogic userBL;
    
    @Override
    public boolean isValid(Action<? extends Result> action) throws ActionException {
        boolean result = super.isValid(action);
        HttpSession session = requestProvider.get().getSession();
        
        User user = new User();
        user.setId((Long)session.getAttribute("user.id"));
        user = userBL.retrieve(user);
        return result && user.getUserRole() == UserRole.ADMIN;
    }
}
