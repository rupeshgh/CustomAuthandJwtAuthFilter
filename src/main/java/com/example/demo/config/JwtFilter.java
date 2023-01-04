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

         String authHeader=request.getHeader(AUTHORIZATION);
        final String jwtToken;
        String email=null;



        System.out.println("From JWT");

//        if(SecurityContextHolder.getContext().getAuthentication()!=null){
//            System.out.println("No token "+authHeader);
//
//
//            Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
//            email=authentication.getName();
//            System.out.println("Request principal"+email);
//
//            UserDetails userDetails=userDetailsService.loadUserByUsername(email);
//            jwtToken=jwtUtils.generateToken(userDetails);
//
//            authHeader="Bearer "+jwtToken;
//            response.setHeader(AUTHORIZATION,authHeader);
//            System.out.println("Generated From auth=null but authenticated==" + jwtToken);
//
//            filterChain.doFilter(request,response);
//            return;
//
//        }




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

            System.out.println("outside");
            filterChain.doFilter(request,response);
        }





    }
}
