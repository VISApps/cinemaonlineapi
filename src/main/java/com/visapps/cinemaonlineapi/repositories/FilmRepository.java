package com.visapps.cinemaonlineapi.repositories;

import com.visapps.cinemaonlineapi.models.*;
import com.visapps.cinemaonlineapi.utils.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sun.rmi.runtime.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilmRepository {

    @Autowired
    DatabaseConnection databaseConnection;

    @Autowired
    UserRepository userRepository;

    public List<FilmItem> getfilms(String name,String year,String genre, String country, String orderby) throws Exception{
        String query = "SELECT * FROM Film WHERE FilmName LIKE  ? AND FilmYear = ISNULL(?, FilmYear) AND Genre = ISNULL(?, Genre) AND Country = ISNULL(?, Country) ORDER BY ";
        if(orderby!=null){
            switch(orderby){
                case "rating":
                    query = query + "Rating DESC";
                    break;
                case "price":
                    query = query + "Cost";
                    break;
                case "year":
                    query = query + "FilmYear DESC";
                    break;
                default:
                    query = query + "FilmID DESC";
                    break;
            }
        }
        else{
            query = query + "FilmID DESC";
        }
        Connection connection = databaseConnection.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(query);
        if(name != null){
           pstmt.setString(1, "%" + name + "%");
        }
        else{
            pstmt.setString(1, "%");
        }
        if(year != null){
            pstmt.setShort(2,Short.valueOf(year));
        }
        else{
            pstmt.setNull(2,Types.SMALLINT);
        }
        if(genre != null){
            pstmt.setString(3,genre);
        }
        else{
            pstmt.setNull(3,Types.VARCHAR);
        }
        if(country != null){
            pstmt.setString(4,country);
        }
        else{
            pstmt.setNull(4,Types.VARCHAR);
        }
        ResultSet resultSet = pstmt.executeQuery();
        List<FilmItem> result = retriveFilmItems(resultSet);
        connection.close();
        return result;
    }

    public FilmModel getFilm(int id) throws Exception{
        String query = "SELECT * FROM Film INNER JOIN Director ON Film.DirectorID = Director.DirectorID WHERE Film.FilmID = ?";
        Connection connection = databaseConnection.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1,id);
        ResultSet resultSet = pstmt.executeQuery();
        FilmModel film = new FilmModel();
        if(resultSet.next()){
            film.setFilmId(resultSet.getInt("FilmId"));
            film.setGenre(resultSet.getString("Genre"));
            film.setCountry(resultSet.getString("Country"));
            film.setDirectorID(resultSet.getInt("DirectorID"));
            film.setFilmName(resultSet.getString("FilmName"));
            film.setFilmYear(resultSet.getShort("FilmYear"));
            film.setFilmLength(resultSet.getTime("FilmLength"));
            film.setAgeLimit(resultSet.getShort("AgeLimit"));
            film.setCost(resultSet.getShort("Cost"));
            film.setRating(resultSet.getBigDecimal("Rating"));
            film.setFDescription(resultSet.getString("FDescription"));
            film.setPosterLink(resultSet.getString("PosterLink"));
            film.setSourceLink(resultSet.getString("SourceLink"));
            film.setDirector(resultSet.getString("FIO"));
        }
        connection.close();
        return film;
    }

    public List<Role> getFilmRoles(int id) throws Exception{
        String query = "SELECT Actor.FIO, Filming.ActorRole FROM Filming INNER JOIN Actor ON Filming.ActorID = Actor.ActorID INNER JOIN Film ON Filming.FilmID = Film.FilmID WHERE Film.FilmID = ?";
        Connection connection = databaseConnection.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1,id);
        ResultSet resultSet = pstmt.executeQuery();
        List<Role> result = new ArrayList<>();
        while(resultSet.next()){
            Role role = new Role();
            role.setActor(resultSet.getString("FIO"));
            role.setRole(resultSet.getString("ActorRole"));
            result.add(role);
        }
        connection.close();
        return result;
    }

    public List<MarkModel> getFilmMarks(int id) throws Exception{
        String query = "SELECT Mark.Comment, Mark.Rating, Mark.DateAndTime, CinemaUser.NickName FROM Mark INNER JOIN CinemaUser On Mark.UserID = CinemaUser.UserID INNER JOIN Film ON Mark.FilmID = Film.FilmID WHERE Film.FilmID = ? ORDER BY Mark.DateAndTime DESC";
        Connection connection = databaseConnection.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1,id);
        ResultSet resultSet = pstmt.executeQuery();
        List<MarkModel> result = new ArrayList<>();
        while(resultSet.next()){
            MarkModel mark = new MarkModel();
            mark.setNickName(resultSet.getString("NickName"));
            mark.setComment(resultSet.getString("Comment"));
            mark.setRating(resultSet.getShort("Rating"));
            mark.setDateAndTime(resultSet.getTimestamp("DateAndTime"));
            result.add(mark);
        }
        connection.close();
        return result;
    }

    public Filters getfilers() throws Exception{
        String yearquery = "SELECT DISTINCT FilmYear FROM Film";
        String countryquery = "SELECT DISTINCT Country FROM Film";
        String genrequery = "SELECT DISTINCT Genre FROM Film";
        List<String> years = new ArrayList<>();
        List<String> countries = new ArrayList<>();
        List<String> genres = new ArrayList<>();
        Connection connection = databaseConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet yeartSet = statement.executeQuery(yearquery);
        while (yeartSet.next())
        {
            years.add(String.valueOf(yeartSet.getShort("FilmYear")));
        }
        connection.close();
        connection = databaseConnection.getConnection();
        statement = connection.createStatement();
        ResultSet countrySet = statement.executeQuery(countryquery);
        while (countrySet.next())
        {
            countries.add(String.valueOf(countrySet.getString("Country")));
        }
        connection.close();
        connection = databaseConnection.getConnection();
        statement = connection.createStatement();
        ResultSet genreSet = statement.executeQuery(genrequery);
        while (genreSet.next())
        {
            genres.add(String.valueOf(genreSet.getString("Genre")));
        }
        connection.close();
        return new Filters(years,countries,genres);
    }

    public Response updateFilmMark(String login, int id, Mark mark) throws Exception{
        String checkquery = "SELECT Mark.UserID FROM Mark INNER JOIN CinemaUser ON CinemaUser.UserID = Mark.UserID WHERE FilmID = ? AND NickName = ?";
        Connection connection = databaseConnection.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(checkquery);
        pstmt.setInt(1,id);
        pstmt.setString(2,login);
        ResultSet resultSet = pstmt.executeQuery();
        int UserID = -1;
        if(resultSet.next()){
            UserID = resultSet.getInt("UserID");
        }
        connection.close();
        if(UserID == -1){
            int UsrID = userRepository.getUserID(login);
            String query = "INSERT INTO Mark (UserID, FilmID, DateAndTime, Rating, Comment) VALUES (?,?,?,?,?)";
            connection = databaseConnection.getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,UsrID);
            pstmt.setInt(2,id);
            pstmt.setTimestamp(3,new Timestamp(System.currentTimeMillis()));
            if(mark.getRating() == 0){
                throw new Exception();
            }
            else{
                pstmt.setShort(4,mark.getRating());
            }
            pstmt.setString(5,mark.getComment());
            pstmt.executeUpdate();
            connection.close();
        }
        else{
            String query = "UPDATE Mark SET DateAndTime = ? , Rating = ? , Comment = ?  WHERE UserID = ? AND FilmID = ?";
            connection = databaseConnection.getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(4,UserID);
            pstmt.setInt(5,id);
            pstmt.setTimestamp(1,new Timestamp(System.currentTimeMillis()));
            if(mark.getRating() == 0){
                throw new Exception();
            }
            else{
                pstmt.setShort(2,mark.getRating());
            }
            pstmt.setString(3,mark.getComment());
            pstmt.executeUpdate();
            connection.close();
        }
        return new Response(200,null);
    }

    public Response storeFilmView(String login, int id) throws Exception{
        String query = "INSERT INTO FilmView (UserID, FilmID, DateAndTime) VALUES (?,?,?)";
        int UserID = userRepository.getUserID(login);
        Connection connection = databaseConnection.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1,UserID);
        pstmt.setInt(2,id);
        pstmt.setTimestamp(3,new Timestamp(System.currentTimeMillis()));
        pstmt.executeUpdate();
        connection.close();
        return new Response(200,null);
    }

    public Mark getFilmMark(String login, int id) throws Exception{
        String query = "SELECT * FROM Mark WHERE FilmID = ? AND UserID = ?";
        int UserID = userRepository.getUserID(login);
        Connection connection = databaseConnection.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1,id);
        pstmt.setInt(2,UserID);
        ResultSet resultSet = pstmt.executeQuery();
        Mark result = new Mark();
        if(resultSet.next()){
            result.setRating(resultSet.getShort("Rating"));
            result.setComment(resultSet.getString("Comment"));
        }
        connection.close();
        return result;
    }

    public Response getFilmFavorite(String login, int id) throws Exception{
        String query = "SELECT * FROM Favorite WHERE FilmID = ? AND UserID = ?";
        int UserID = userRepository.getUserID(login);
        Connection connection = databaseConnection.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1,id);
        pstmt.setInt(2,UserID);
        ResultSet resultSet = pstmt.executeQuery();
        Response response = new Response(0, null);
        if(resultSet.next()){
            response = new Response(1,null);
        }
        connection.close();
        return response;
    }

    public Response updateFilmFavorite(String login, int id) throws Exception{
        String query = "SELECT * FROM Favorite WHERE FilmID = ? AND UserID = ?";
        int UserID = userRepository.getUserID(login);
        Connection connection = databaseConnection.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1,id);
        pstmt.setInt(2,UserID);
        ResultSet resultSet = pstmt.executeQuery();
        boolean exists = resultSet.next();
        connection.close();
        if(!exists){
            query = "INSERT INTO Favorite (UserID, FilmID, DateAndTime) VALUES (?,?,?)";
            connection = databaseConnection.getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,UserID);
            pstmt.setInt(2,id);
            pstmt.setTimestamp(3,new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
            connection.close();
        }
        else{
            query = "DELETE FROM Favorite WHERE UserID = ? AND FilmID = ?";
            connection = databaseConnection.getConnection();
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,UserID);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            connection.close();
        }
        return new Response(200,null);
    }

    private List<FilmItem> retriveFilmItems(ResultSet resultSet) throws Exception{
        List<FilmItem> result = new ArrayList<>();
        while (resultSet.next())
        {
            FilmItem film = new FilmItem();
            film.setFilmId(resultSet.getInt("FilmId"));
            film.setFilmName(resultSet.getString("FilmName"));
            film.setAgeLimit(resultSet.getShort("AgeLimit"));
            film.setCost(resultSet.getShort("Cost"));
            film.setPosterLink(resultSet.getString("PosterLink"));
            result.add(film);
        }
        return result;
    }
}
