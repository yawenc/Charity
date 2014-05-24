package com.bertazoli.charity.shared.action.user;

import java.util.ArrayList;

import com.bertazoli.charity.shared.beans.UserDonation;
import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure=true)
public class SearchMyDonations {
    @Out(1) ArrayList<UserDonation> donations;
}
