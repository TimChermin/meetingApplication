package com.meetingapp.meetingapp.models;

public class UserModel {
    String alias;
    String address;

    public UserModel(String alias, String address) {
        this.alias = alias;
        this.address = address;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
