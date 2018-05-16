package com.visapps.cinemaonlineapi.controllers;

import com.visapps.cinemaonlineapi.models.Film;
import com.visapps.cinemaonlineapi.models.Stats;
import com.visapps.cinemaonlineapi.models.User;
import com.visapps.cinemaonlineapi.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminRepository adminRepository;

    @RequestMapping(value ="/getstats", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Stats> getstats(){
        try{
            Stats result = adminRepository.getStats();
            return new ResponseEntity<Stats>(result, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<Stats>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
