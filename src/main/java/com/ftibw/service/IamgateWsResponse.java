package com.ftibw.service;

import java.io.Serializable;

/**
 * @author : Ftibw
 * @date : 2019/2/12 18:38
 */
public class IamgateWsResponse implements Serializable {

    private String status;
    private String message;
    private Integer code;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
