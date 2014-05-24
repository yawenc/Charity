package com.bertazoli.charity.server.guice;

import com.bertazoli.charity.server.servlet.ActivateUserServlet;
import com.bertazoli.charity.server.servlet.ApplicationStartup;
import com.bertazoli.charity.server.servlet.DoExpressCheckoutServlet;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

public class GuiceServletConfig extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new ServerModule(),
                new DispatchServletModule(),
                new ServletModule() {
            @Override
            protected void configureServlets() {
                serve("/activateUser").with(ActivateUserServlet.class);
                serve("/doExpressCheckout").with(DoExpressCheckoutServlet.class);
                serve("/doNothing").with(ApplicationStartup.class);
            }
        });
    }
}
