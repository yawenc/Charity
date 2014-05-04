package com.bertazoli.charity.shared.action.user;

import com.bertazoli.charity.shared.beans.User;
import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure=false)
public class FetchUserFromSession {
    @In(1) String sessionID;
    @Out(1) User user;
}
