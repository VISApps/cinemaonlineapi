package com.visapps.cinemaonlineapi.models;

import java.util.List;

public class Auth {

    private boolean SUCCESS;
    private List<String> ROLES;

    public Auth(boolean SUCCESS, List<String> ROLES){
        this.SUCCESS = SUCCESS;
        this.ROLES = ROLES;
    }

    public boolean isSUCCESS() {
        return SUCCESS;
    }

    public void setSUCCESS(boolean SUCCESS) {
        this.SUCCESS = SUCCESS;
    }

    public List<String> getROLES() {
        return ROLES;
    }

    public void setROLES(List<String> ROLES) {
        this.ROLES = ROLES;
    }
}
