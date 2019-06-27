package com.csi.sbs.sysadmin.business.clientmodel;



public class IDModel {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
}