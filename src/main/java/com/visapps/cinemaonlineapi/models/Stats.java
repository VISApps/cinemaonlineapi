package com.visapps.cinemaonlineapi.models;

import java.util.List;

public class Stats {

    private int users;
    private int films;
    private int views;
    private List<FilmStats> topfilms;

    public int getUsers() {
        return users;
    }

    public void setUsers(int users) {
        this.users = users;
    }

    public int getFilms() {
        return films;
    }

    public void setFilms(int films) {
        this.films = films;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public List<FilmStats> getTopfilms() {
        return topfilms;
    }

    public void setTopfilms(List<FilmStats> topfilms) {
        this.topfilms = topfilms;
    }
}
