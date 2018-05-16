package com.visapps.cinemaonlineapi.controllers;

import com.visapps.cinemaonlineapi.models.*;
import com.visapps.cinemaonlineapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user/profile")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value ="/getprofile", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> getprofile(Authentication authentication){
        String login = authentication.getName();
        try{
            User result = userRepository.getUser(login);
            //User result = new User();
            //result.setNickName(login);
            return new ResponseEntity<User>(result, HttpStatus.OK);
        }
        catch(Exception e){
            User result = new User();
            result.setNickName(e.getMessage());
            return new ResponseEntity<User>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value ="/updateprofile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Response> updateprofile(@RequestBody User user, Authentication authentication){
        String login = authentication.getName();
        try{
            Response result = userRepository.updateUser(login,user);
            return new ResponseEntity<Response>(result, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<Response>(new Response(500,e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value ="/gethistory", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Film>> gethistory(Authentication authentication){
        String login = authentication.getName();
        try{
            List<Film> result = userRepository.getHistory(login);
            return new ResponseEntity<List<Film>>(result, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<List<Film>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value ="/getfavorites", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Film>> getfavorites(Authentication authentication){
        String login = authentication.getName();
        try{
            List<Film> result = userRepository.getFavorites(login);
            return new ResponseEntity<List<Film>>(result, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<List<Film>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
