package com.visapps.cinemaonlineapi.models;

public class FilmItem extends Film{

    private short AgeLimit;
    private short Cost;
    private String PosterLink;

    public short getAgeLimit() {
        return AgeLimit;
    }

    public void setAgeLimit(short ageLimit) {
        AgeLimit = ageLimit;
    }

    public short getCost() {
        return Cost;
    }

    public void setCost(short cost) {
        Cost = cost;
    }

    public String getPosterLink() {
        return PosterLink;
    }

    public void setPosterLink(String posterLink) {
        PosterLink = posterLink;
    }
}
