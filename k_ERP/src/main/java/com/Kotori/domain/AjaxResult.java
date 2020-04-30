package com.Kotori.domain;

public class AjaxResult {
    Boolean result;
    String msg;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "AjaxResult{" +
                "result=" + result +
                ", msg='" + msg + '\'' +
                '}';
    }
}
