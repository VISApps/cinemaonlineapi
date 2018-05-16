package com.visapps.cinemaonlineapi.repositories;

import com.visapps.cinemaonlineapi.models.FilmStats;
import com.visapps.cinemaonlineapi.models.Stats;
import com.visapps.cinemaonlineapi.utils.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AdminRepository {

    @Autowired
    DatabaseConnection databaseConnection;

    public Stats getStats() throws Exception{
        String usersquery = "SELECT COUNT(*) AS 'UserCount' FROM CinemaUser";
        String filmsquery = "SELECT COUNT(*) AS 'FilmsCount' FROM Film";
        String viewsquery = "SELECT COUNT(*) AS 'ViewsCount' FROM FilmView";
        String topfilmsquery = "SELECT Film.FilmID, Film.FilmName, COUNT(FilmView.DateAndTime) AS 'ViewsCount' FROM Film INNER JOIN FilmView ON Film.FilmID = FilmView.FilmID GROUP BY Film.FilmID, Film.FilmName ORDER BY COUNT(FilmView.DateAndTime) DESC, Film.FilmID, Film.FilmName";
        Stats result = new Stats();
        Connection connection = databaseConnection.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(usersquery);
        ResultSet resultSet = pstmt.executeQuery();
        resultSet.next();
        result.setUsers(resultSet.getInt("UserCount"));
        connection.close();
        connection = databaseConnection.getConnection();
        pstmt = connection.prepareStatement(filmsquery);
        resultSet = pstmt.executeQuery();
        resultSet.next();
        result.setFilms(resultSet.getInt("FilmsCount"));
        connection.close();
        connection = databaseConnection.getConnection();
        pstmt = connection.prepareStatement(viewsquery);
        resultSet = pstmt.executeQuery();
        resultSet.next();
        result.setViews(resultSet.getInt("ViewsCount"));
        connection.close();
        connection = databaseConnection.getConnection();
        pstmt = connection.prepareStatement(topfilmsquery);
        resultSet = pstmt.executeQuery();
        List<FilmStats> filmStats = new ArrayList<>();
        while(resultSet.next()){
            FilmStats film = new FilmStats();
            film.setFilmId(resultSet.getInt("FilmID"));
            film.setFilmName(resultSet.getString("FilmName"));
            film.setViews(resultSet.getInt("ViewsCount"));
            filmStats.add(film);
        }
        connection.close();
        result.setTopfilms(filmStats);
        return result;
    }
}
