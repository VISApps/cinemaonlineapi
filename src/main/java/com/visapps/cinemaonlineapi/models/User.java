package com.visapps.cinemaonlineapi.models;

import java.util.Date;

public class User {

    private char UserType;
    private String NickName;
    private String Passwd;
    private String Email;
    private String FIO;
    private char Gender;
    private Date Birthday = null;
    private Date JoinDate = null;

    public User(){

    }

    public char getUserType() {
        return UserType;
    }

    public void setUserType(char userType) {
        UserType = userType;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getPasswd() {
        return Passwd;
    }

    public void setPasswd(String passwd) {
        Passwd = passwd;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public char getGender() {
        return Gender;
    }

    public void setGender(char gender) {
        Gender = gender;
    }

    public Date getBirthday() {
        return Birthday;
    }

    public void setBirthday(Date birthday) {
        Birthday = birthday;
    }

    public Date getJoinDate() {
        return JoinDate;
    }

    public void setJoinDate(Date joinDate) {
        JoinDate = joinDate;
    }
}
