package com.ahad.devicemanager.controller;

public class ApiResponse {
    private int code;
    private String message;
    private Object content;
    public ApiResponse(int code, String message, Object content) {
        this.code = code;
        this.message = message;
        this.content = content;

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
