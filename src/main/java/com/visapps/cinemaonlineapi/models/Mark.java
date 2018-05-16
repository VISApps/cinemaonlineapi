package com.visapps.cinemaonlineapi.models;

public class Mark {

    private short Rating = 0;
    private String Comment;

    public short getRating() {
        return Rating;
    }

    public void setRating(short rating) {
        Rating = rating;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
