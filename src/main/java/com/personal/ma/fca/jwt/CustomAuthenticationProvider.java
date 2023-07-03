package com.personal.ma.fca.jwt;

import com.personal.ma.fca.jwt.resources.AuthenticationException;
import com.personal.ma.fca.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

//https://www.baeldung.com/spring-security-authentication-provider
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        // if (shouldAuthenticateAgainstThirdPartySystem()) {

        // use the credentials
        // and authenticate against the third-party system
        return new UsernamePasswordAuthenticationToken(
                name, password, new ArrayList<>());
       /* } else {
            return null;
        }*/
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}