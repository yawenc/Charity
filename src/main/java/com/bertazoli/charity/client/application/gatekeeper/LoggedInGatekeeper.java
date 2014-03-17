package com.bertazoli.charity.client.application.gatekeeper;

import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;

@Singleton
public class LoggedInGatekeeper implements Gatekeeper {

    @Override
    public boolean canReveal() {
        return false;
    }
}
