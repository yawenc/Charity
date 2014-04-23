package com.bertazoli.charity.shared.action;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class SearchUsername {
    @In(1) String username;
    @Out(1) Boolean exists;
}
