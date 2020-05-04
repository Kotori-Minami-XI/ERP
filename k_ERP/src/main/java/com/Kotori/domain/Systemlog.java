package com.Kotori.domain;

import java.util.Date;

public class Systemlog {
    private Long id;

    private Date optime;

    private String ip;

    private String function;

    private String params;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOptime() {
        return optime;
    }

    public void setOptime(Date optime) {
        this.optime = optime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function == null ? null : function.trim();
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    @Override
    public String toString() {
        return "Systemlog{" +
                "id=" + id +
                ", optime=" + optime +
                ", ip='" + ip + '\'' +
                ", function='" + function + '\'' +
                ", params='" + params + '\'' +
                '}';
    }
}