package com.bertazoli.charity.shared.action.donate;

import com.bertazoli.charity.shared.beans.DonationInformation;
import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure=true)
public class SetExpressChecktout {
    @In(1) DonationInformation input;
    @Out(1) String output;
}
