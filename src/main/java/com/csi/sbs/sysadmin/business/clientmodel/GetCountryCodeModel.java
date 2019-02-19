package com.csi.sbs.sysadmin.business.clientmodel;

public class GetCountryCodeModel {

    private String countrycode;

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode == null ? null : countrycode.trim();
    }
}