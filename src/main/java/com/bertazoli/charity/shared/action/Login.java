package com.bertazoli.charity.shared.action;

import com.bertazoli.charity.shared.beans.User;
import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

@GenDispatch(isSecure=false, serviceName=UnsecuredActionImpl.DEFAULT_SERVICE_NAME)
public class Login {
    @In(1) String username;
    @In(2) String password;
    @Out(1) String sessionKey;
    @Out(2) User user;
}
