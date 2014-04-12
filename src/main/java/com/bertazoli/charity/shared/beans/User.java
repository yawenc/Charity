package com.bertazoli.charity.shared.beans;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bertazoli.charity.shared.util.Util;
import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@Table(name = "user")
public class User implements IsSerializable, HasValidation {

    @Id @GeneratedValue private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private Timestamp dob;
    private String salt;
    private String password;
    @Transient private boolean isLoggedIn;

    public Timestamp getDob() {
        return dob;
    }

    public void setDob(Timestamp dob) {
        this.dob = dob;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public boolean validate() {
        if (Util.isNullOrEmpty(getUsername()) || getUsername().length() < 30 || getUsername().length() > 40) {
            return false;
        }

        return true;
    }
}
