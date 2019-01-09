package com.csi.sbs.sysadmin.business.entity;

import java.math.BigDecimal;

public class UserEntity {
    private String id;

    private String userid;

    private String username;

    private String userposition;

    private BigDecimal transactionlimit;

    private BigDecimal tdlimit;

    private BigDecimal exchlimit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getUserposition() {
        return userposition;
    }

    public void setUserposition(String userposition) {
        this.userposition = userposition == null ? null : userposition.trim();
    }

    public BigDecimal getTransactionlimit() {
        return transactionlimit;
    }

    public void setTransactionlimit(BigDecimal transactionlimit) {
        this.transactionlimit = transactionlimit;
    }

    public BigDecimal getTdlimit() {
        return tdlimit;
    }

    public void setTdlimit(BigDecimal tdlimit) {
        this.tdlimit = tdlimit;
    }

    public BigDecimal getExchlimit() {
        return exchlimit;
    }

    public void setExchlimit(BigDecimal exchlimit) {
        this.exchlimit = exchlimit;
    }
}