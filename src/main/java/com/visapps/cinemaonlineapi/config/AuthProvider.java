package com.visapps.cinemaonlineapi.config;

import com.visapps.cinemaonlineapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        int result;
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        try{
            result = userRepository.authUser(login, password);
        }
        catch(Exception e){
            result = 500;
        }
        switch(result){
            case 200:
                grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
                return new UsernamePasswordAuthenticationToken(
                        login, password, grantedAuths);
            case 201:
                grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
                grantedAuths.add(new SimpleGrantedAuthority("ROLE_MODERATOR"));
                return new UsernamePasswordAuthenticationToken(
                        login, password, grantedAuths);
            case 202:
                grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
                grantedAuths.add(new SimpleGrantedAuthority("ROLE_MODERATOR"));
                grantedAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                return new UsernamePasswordAuthenticationToken(
                        login, password, grantedAuths);
            case 401:
                throw new BadCredentialsException("Login or password incorrect");
            case 403:
                throw new DisabledException("Account bloacked");
            case 500:
                throw new RuntimeException();
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}
