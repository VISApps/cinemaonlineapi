package com.visapps.cinemaonlineapi.models;

public class Response {

    private int code;
    private String details;

    public Response(int code){
        this.code = code;
    }

    public Response(int code, String details){
        this.code = code;
        this.details = details;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
