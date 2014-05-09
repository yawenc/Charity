package com.bertazoli.charity.shared.action;

import com.bertazoli.charity.shared.beans.User;
import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

@GenDispatch(isSecure = false, serviceName=UnsecuredActionImpl.DEFAULT_SERVICE_NAME)
public class Signup {
    @In(1) User userIn;
    @Out(1) User userOut;
}
