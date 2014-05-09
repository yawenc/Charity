package com.bertazoli.charity.server.guice;

import com.bertazoli.charity.shared.SharedTokens;
import com.google.inject.servlet.ServletModule;
import com.gwtplatform.dispatch.rpc.server.guice.DispatchServiceImpl;
import com.gwtplatform.dispatch.rpc.shared.ActionImpl;
import com.gwtplatform.dispatch.shared.SecurityCookie;

public class DispatchServletModule extends ServletModule {
    @Override
    public void configureServlets() {
        
        bindConstants();
        
        serve("/" + ActionImpl.DEFAULT_SERVICE_NAME + "*").with(DispatchServiceImpl.class);
    }

    private void bindConstants() {
        bindConstant().annotatedWith(SecurityCookie.class).to(SharedTokens.securityCookieName);
    }
}
