package com.meetingapp.meetingapp.models;

public class ExchangeContact {
    private String address;
    private String alias;
    private String type;

    public ExchangeContact(String address, String alias, String type) {
        this.address = address;
        this.alias = alias;
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
