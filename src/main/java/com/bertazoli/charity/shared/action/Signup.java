package com.bertazoli.charity.shared.action;

import com.bertazoli.charity.shared.beans.User;
import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class Signup {
    @In(1) User userIn;
    @Out(1) User userOut;
}
