package com.visapps.cinemaonlineapi.controllers;

import com.visapps.cinemaonlineapi.models.Response;
import com.visapps.cinemaonlineapi.models.User;
import com.visapps.cinemaonlineapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
public class RegisterController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value ="/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Response> register(@RequestBody User user){
        user.setUserType('п');
        try{
            Response result = userRepository.addUser(user);
            return new ResponseEntity<Response>(result, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<Response>(new Response(500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value ="/login", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Response> login(){
        try{
            int value = userRepository.authUser("Battlevisek", "Qwerty45");
            return new ResponseEntity<Response>(new Response(value), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<Response>(new Response(500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
