package com.bertazoli.charity.client.application;

import com.bertazoli.charity.client.application.admin.AdminModule;
import com.bertazoli.charity.client.application.charity.RegisterCharityModule;
import com.bertazoli.charity.client.application.donate.DonateModule;
import com.bertazoli.charity.client.application.error.general.ErrorModule;
import com.bertazoli.charity.client.application.error.transaction.TransactionErrorModule;
import com.bertazoli.charity.client.application.header.HeaderModule;
import com.bertazoli.charity.client.application.home.HomeModule;
import com.bertazoli.charity.client.application.login.LoginModule;
import com.bertazoli.charity.client.application.main.MainModule;
import com.bertazoli.charity.client.application.signup.SignupModule;
import com.bertazoli.charity.client.application.signup.usercreated.UserCreatedModule;
import com.bertazoli.charity.client.application.usersettings.UserSettingsModule;
import com.bertazoli.charity.client.application.widgets.charity.CharityItemModule;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ApplicationModule extends AbstractPresenterModule {
    
    @Override
    protected void configure() {
        install(new RegisterCharityModule());
        install(new SignupModule());
        install(new UserSettingsModule());
        install(new MainModule());
        install(new LoginModule());
        install(new HomeModule());
        install(new UserCreatedModule());
        install(new CharityItemModule());
        install(new DonateModule());
        install(new HeaderModule());
        install(new ErrorModule());
        install(new TransactionErrorModule());
        install(new AdminModule());

        bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class, ApplicationPresenter.MyProxy.class);

    }
}
