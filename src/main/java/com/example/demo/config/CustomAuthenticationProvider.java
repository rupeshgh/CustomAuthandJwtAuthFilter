package com.example.demo.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;


public class CustomAuthenticationProvider implements AuthenticationProvider {


@Autowired
    PasswordEncoder passwordEncoder;

@Autowired
UserDetailsService userDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username=authentication.getName();
        String password=authentication.getCredentials().toString();

        System.out.println("From custom auth provider");

        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(passwordEncoder.encode(password).equals(userDetails.getPassword())) {

                return new UsernamePasswordAuthenticationToken(username, passwordEncoder.encode(password), userDetails.getAuthorities());
            }
            else{
                throw new UsernameNotFoundException("bad credential");
            }
        }
        catch (Exception e){
            throw new UsernameNotFoundException("Bad Credential");
        }



    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
