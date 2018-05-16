package com.visapps.cinemaonlineapi.models;

import java.math.BigDecimal;
import java.sql.Time;

public class FilmModel extends FilmItem{

    private String Genre;
    private String Country;
    private int DirectorID;
    private short FilmYear;
    private Time FilmLength;
    private BigDecimal Rating;
    private String FDescription;
    private String SourceLink;
    private String Director;

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public int getDirectorID() {
        return DirectorID;
    }

    public void setDirectorID(int directorID) {
        DirectorID = directorID;
    }

    public short getFilmYear() {
        return FilmYear;
    }

    public void setFilmYear(short filmYear) {
        FilmYear = filmYear;
    }

    public Time getFilmLength() {
        return FilmLength;
    }

    public void setFilmLength(Time filmLength) {
        FilmLength = filmLength;
    }

    public BigDecimal getRating() {
        return Rating;
    }

    public void setRating(BigDecimal rating) {
        Rating = rating;
    }

    public String getFDescription() {
        return FDescription;
    }

    public void setFDescription(String FDescription) {
        this.FDescription = FDescription;
    }

    public String getSourceLink() {
        return SourceLink;
    }

    public void setSourceLink(String sourceLink) {
        SourceLink = sourceLink;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }
}
