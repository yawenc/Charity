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

    
}
