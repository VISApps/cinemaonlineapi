package com.visapps.cinemaonlineapi.repositories;

import com.visapps.cinemaonlineapi.models.Film;
import com.visapps.cinemaonlineapi.models.Response;
import com.visapps.cinemaonlineapi.models.User;
import com.visapps.cinemaonlineapi.utils.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    DatabaseConnection databaseConnection;

    public int authUser(String login, String password) throws Exception{
        String query = "{call UserLogin(?,?,?)}";
        Connection connection = databaseConnection.getConnection();
        CallableStatement proc = connection.prepareCall(query);
        proc.setString(1,login);
        proc.setString(2,password);
        proc.registerOutParameter(3, Types.INTEGER);
        proc.execute();
        int returnValue = proc.getInt(3);
        connection.close();
        return returnValue;
    }

    public int getUserID(String login) throws Exception{
        String query = "SELECT UserID FROM CinemaUser WHERE NickName = ?";
        Connection connection = databaseConnection.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1,login);
        ResultSet resultSet = pstmt.executeQuery();
        resultSet.next();
        int result = resultSet.getInt("UserID");
        connection.close();
        return result;
    }

    public Response addUser(User user) throws Exception{
        String query = "{call UserRegister(?,?,?,?,?,?,?,?)}";
        Connection connection = databaseConnection.getConnection();
        CallableStatement proc = connection.prepareCall(query);
        proc.setString(1,String.valueOf(user.getUserType()));
        proc.setString(2,user.getNickName());
        proc.setString(3, user.getPasswd());
        proc.setString(4, user.getEmail());
        proc.setString(5,user.getFIO());
        proc.setString(6,null);
        if(user.getBirthday() == null){
            proc.setDate(7,null);
        }
        else{
            proc.setDate(7,new Date(user.getBirthday().getTime()));
        }
        proc.registerOutParameter(8, Types.INTEGER);
        proc.execute();
        int returnValue = proc.getInt(8);
        connection.close();
        return new Response(returnValue);
    }

    public User getUser(String login) throws Exception{
        String query = "SELECT * FROM CinemaUser WHERE NickName = '" + login + "'";
        Connection connection = databaseConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        User user = new User();
        user.setUserType(resultSet.getString("UserType").charAt(0));
        user.setEmail(resultSet.getString("Email"));
        user.setBirthday(resultSet.getDate("Birthday"));
        user.setFIO(resultSet.getString("FIO"));
        try{
            user.setGender(resultSet.getString("Gender").charAt(0));
        }
        catch(Exception e){

        }
        user.setNickName(resultSet.getString("NickName"));
        user.setJoinDate(resultSet.getDate("JoinDate"));
        connection.close();
        return user;
    }

    public Response updateUser(String login, User user) throws Exception{
        String sql = "UPDATE CinemaUser SET Passwd = ? , FIO = ? , Gender = ? , Birthday = ? WHERE NickName = ?";
        Connection connection = databaseConnection.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(sql);
        if(user.getPasswd() != null){
            pstmt.setString(1,user.getPasswd());
        }
        else{
            throw new Exception();
        }
        if(user.getFIO() != null){
            pstmt.setString(2,user.getFIO());
        }
        else{
            pstmt.setString(2,null);
        }
        if(user.getGender() != '\u0000'){
            pstmt.setString(3,String.valueOf(user.getGender()));
        }
        else{
            pstmt.setString(3,null);
        }
        if(user.getBirthday() != null){
            pstmt.setDate(4,new Date(user.getBirthday().getTime()));
        }
        else{
            pstmt.setDate(4,null);
        }
        pstmt.setString(5,login);
        pstmt.executeUpdate();
        connection.close();
        return new Response(200,null);
    }

    public List<Film> getHistory(String login) throws Exception{
        String query = "SELECT Film.FilmID, Film.FilmName, MAX(FilmView.DateAndTime) FROM Film INNER JOIN FilmView ON Film.FilmID = FilmView.FilmID WHERE UserID = ? GROUP BY Film.FilmName, Film.FilmID ORDER BY MAX(FilmView.DateAndTime) DESC, Film.FilmName, Film.FilmID";
        int UserID = getUserID(login);
        Connection connection = databaseConnection.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1,UserID);
        ResultSet resultSet = pstmt.executeQuery();
        List<Film> result = new ArrayList<>();
        while(resultSet.next()){
            Film film = new Film();
            film.setFilmId(resultSet.getInt("FilmID"));
            film.setFilmName(resultSet.getString("FilmName"));
            result.add(film);
        }
        return result;
    }

    public List<Film> getFavorites(String login) throws Exception{
        String query = "SELECT Film.FilmID, Film.FilmName FROM Film INNER JOIN Favorite ON Favorite.FilmID = Film.FilmID WHERE UserID = ? ORDER BY DateAndTime DESC";
        int UserID = getUserID(login);
        Connection connection = databaseConnection.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1,UserID);
        ResultSet resultSet = pstmt.executeQuery();
        List<Film> result = new ArrayList<>();
        while(resultSet.next()){
            Film film = new Film();
            film.setFilmId(resultSet.getInt("FilmID"));
            film.setFilmName(resultSet.getString("FilmName"));
            result.add(film);
        }
        return result;
    }


}
