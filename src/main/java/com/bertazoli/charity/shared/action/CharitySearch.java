package com.bertazoli.charity.shared.action;

import java.util.ArrayList;

import com.bertazoli.charity.shared.beans.Charity;
import com.bertazoli.charity.shared.searchparams.CharitySearchParams;
import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure=true)
public class CharitySearch {
    @In(1) CharitySearchParams params;
    @Out(1) ArrayList<Charity> charities;
}
