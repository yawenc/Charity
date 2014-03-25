package com.bertazoli.charity.shared.action;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure=false)
public class Login {
    @In(1) String username;
    @In(2) String password;
    @Out(1) String sessionKey;
}
