package com.visapps.cinemaonlineapi.controllers;

import com.visapps.cinemaonlineapi.models.Auth;
import com.visapps.cinemaonlineapi.models.Film;
import com.visapps.cinemaonlineapi.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class AuthController {

    @RequestMapping(value ="/auth", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Auth> login(Authentication authentication){
        GrantedAuthority[] authorities = new GrantedAuthority[authentication.getAuthorities().size()];
        authentication.getAuthorities().toArray(authorities);
        List<String> roles = new ArrayList<>();
        for(GrantedAuthority authority : authorities){
            roles.add(authority.toString());
        }
        Auth auth = new Auth(true, roles);
        return new ResponseEntity<Auth>(auth, HttpStatus.OK);
    }
}
