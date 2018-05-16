package com.visapps.cinemaonlineapi.models;

import java.util.Date;

public class MarkModel extends Mark{

    private String NickName;
    private Date DateAndTime;

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public Date getDateAndTime() {
        return DateAndTime;
    }

    public void setDateAndTime(Date dateAndTime) {
        DateAndTime = dateAndTime;
    }
}
