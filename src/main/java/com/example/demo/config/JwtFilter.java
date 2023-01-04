package com.example.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Principal;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;



public class JwtFilter extends OncePerRequestFilter {


    @Autowired
    private  JwtUtils jwtUtils;

  @Autowired
    UserDetailsService userDetailsService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Reading authorization header
         String authHeader=request.getHeader("Authorization");
        final String jwtToken;
        String email=null;



        System.out.println("From JWT");


        //If auth header exists:Extract username/email validate user and continue
        if(authHeader!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
            jwtToken=authHeader.substring(7,authHeader.length());

            email=jwtUtils.extractUsername(jwtToken);

            System.out.println("Jwt extracted email"+ email);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            final boolean validToken=jwtUtils.validateToken(jwtToken,userDetails);

            if (validToken) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


            }
            filterChain.doFilter(request,response);

        }

        else{

            filterChain.doFilter(request,response);
        }





    }
}
