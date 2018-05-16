package com.visapps.cinemaonlineapi.models;

import java.math.BigDecimal;
import java.sql.Time;

public class Film {

    private int FilmId;
    private String FilmName;

    public int getFilmId() {
        return FilmId;
    }

    public void setFilmId(int filmId) {
        FilmId = filmId;
    }

    public String getFilmName() {
        return FilmName;
    }

    public void setFilmName(String filmName) {
        FilmName = filmName;
    }
}
