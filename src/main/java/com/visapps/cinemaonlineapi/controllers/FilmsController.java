package com.visapps.cinemaonlineapi.controllers;

import com.visapps.cinemaonlineapi.models.*;
import com.visapps.cinemaonlineapi.repositories.FilmRepository;
import com.visapps.cinemaonlineapi.utils.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping("/api/user/films")
public class FilmsController {

    @Autowired
    FilmRepository filmRepository;

    @RequestMapping(value ="/getfilms", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<FilmItem>> getfilms(@RequestParam(value="name", required=false) String name,
                                               @RequestParam(value="year", required=false) String year,
                                               @RequestParam(value="genre", required=false) String genre,
                                               @RequestParam(value="country" , required=false) String country,
                                               @RequestParam(value="orderby", required=false) String orderby

    ) {
        try{
            List<FilmItem> result = filmRepository.getfilms(name,year,genre,country,orderby);
            return new ResponseEntity<List<FilmItem>>(result, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<List<FilmItem>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value ="/getfilm", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<FilmModel> getfilm(@RequestParam(value="id") int id) {
        try{
            FilmModel result = filmRepository.getFilm(id);
            return new ResponseEntity<FilmModel>(result, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<FilmModel>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value ="/getroles", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Role>> getroles(@RequestParam(value="id") int id) {
        try{
            List<Role> result = filmRepository.getFilmRoles(id);
            return new ResponseEntity<List<Role>>(result, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<List<Role>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value ="/getmarks", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<MarkModel>> getmarks(@RequestParam(value="id") int id) {
        try{
            List<MarkModel> result = filmRepository.getFilmMarks(id);
            return new ResponseEntity<List<MarkModel>>(result, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<List<MarkModel>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value ="/getfilters", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Filters> getfilters() {
        try{
            Filters result = filmRepository.getfilers();
            return new ResponseEntity<Filters>(result, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<Filters>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value ="/updatemark", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Response> updatemark(@RequestParam(value="id") int id, @RequestBody Mark mark, Authentication authentication) {
        try{
            String login = authentication.getName();
            Response result = filmRepository.updateFilmMark(login, id, mark);
            return new ResponseEntity<Response>(result, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<Response>(new Response(500,null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value ="/getmark", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Mark> getmark(@RequestParam(value="id") int id, Authentication authentication) {
        try{
            String login = authentication.getName();
            Mark result = filmRepository.getFilmMark(login, id);
            return new ResponseEntity<Mark>(result, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<Mark>(new Mark(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value ="/getfavorite", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Response> getfavorite(@RequestParam(value="id") int id, Authentication authentication) {
        try{
            String login = authentication.getName();
            Response result = filmRepository.getFilmFavorite(login,id);
            return new ResponseEntity<Response>(result, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<Response>(new Response(500,null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value ="/updatefavorite", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Response> updatefavorite(@RequestParam(value="id") int id, Authentication authentication) {
        try{
            String login = authentication.getName();
            Response result = filmRepository.updateFilmFavorite(login,id);
            return new ResponseEntity<Response>(result, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<Response>(new Response(500,null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value ="/storeview", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Response> storeview(@RequestParam(value="id") int id, Authentication authentication) {
        try{
            String login = authentication.getName();
            Response result = filmRepository.storeFilmView(login,id);
            return new ResponseEntity<Response>(result, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<Response>(new Response(500,e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
