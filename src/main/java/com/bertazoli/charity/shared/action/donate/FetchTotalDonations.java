package com.bertazoli.charity.shared.action.donate;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure=true)
public class FetchTotalDonations {
    @Out(1) Double output;
}
