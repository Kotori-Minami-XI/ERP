package com.Kotori.domain;

public class Permission {
    private Long pid;

    private String pname;

    private String presource;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname == null ? null : pname.trim();
    }

    public String getPresouce() {
        return presource;
    }

    public void setPresouce(String presouce) {
        this.presource = presouce == null ? null : presouce.trim();
    }

    @Override
    public String toString() {
        return "Permission{" +
                "pid=" + pid +
                ", pname='" + pname + '\'' +
                ", presouce='" + presource + '\'' +
                '}';
    }
}