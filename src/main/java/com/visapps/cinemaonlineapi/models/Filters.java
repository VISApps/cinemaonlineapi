package com.visapps.cinemaonlineapi.models;

import java.util.List;

public class Filters {

    private List<String> years;
    private List<String> countries;
    private List<String> genres;

    public Filters(List<String> years, List<String> countries, List<String> genres){
        this.years = years;
        this.countries = countries;
        this.genres = genres;
    }

    public List<String> getYears() {
        return years;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}
