package com.bertazoli.charity.client.i18n;

import com.google.gwt.i18n.client.LocalizableResource.DefaultLocale;
import com.google.gwt.i18n.client.LocalizableResource.Generate;
import com.google.gwt.i18n.client.LocalizableResource.GenerateKeys;
import com.google.gwt.i18n.client.Messages;

@GenerateKeys()
@DefaultLocale("en_US")
@Generate(format = {"com.google.gwt.i18n.rebind.format.PropertiesFormat"})
public interface GlobalDictionary extends Messages {

    @DefaultMessage("Username already exists")
    String usernameAlreadyExists();

    @DefaultMessage("Password does not match")
    String passwordDoesNotMatch();

    @DefaultMessage("###,##0.00")
    String currencyFormat();

    @DefaultMessage("Country")
    String country();

    @DefaultMessage("State")
    String state();

    @DefaultMessage("Value has to be multiple of {0}")
    String valueHasToBeMultipleOf(int multipleOf);

    @DefaultMessage("Total that have been donated ${0}")
    String totalThatHaveBeenDonated(String donated);
}
