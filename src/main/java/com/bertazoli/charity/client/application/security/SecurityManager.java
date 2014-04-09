package com.bertazoli.charity.client.application.security;

import com.bertazoli.charity.shared.beans.User;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class SecurityManager {

    @Inject
    public SecurityManager() {
    }

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
