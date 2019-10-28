package com.example.administrator.l_json2.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/10/27 0027.
 */

public class HttpResult1 {
    private int code;
    private String message;
    private List<Menu> data;

    public HttpResult1(int code, String message, List<Menu> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<Menu> getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(List<Menu> data) {
        this.data = data;
    }
}
