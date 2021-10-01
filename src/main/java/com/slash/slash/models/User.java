package com.slash.slash.models;

import javax.persistence.Entity;

@Entity
public class User extends GenericUser {

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
