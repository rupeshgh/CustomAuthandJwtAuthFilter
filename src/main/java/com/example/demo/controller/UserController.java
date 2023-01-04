package com.example.demo.controller;


import com.example.demo.config.JwtUtils;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URI;
import java.security.Principal;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserDetailsService customUserDetailsService;

    @Autowired
    JwtUtils jwtUtils;


    @GetMapping("/genToken")
    public ResponseEntity<?> generateJwtToken(Principal principal, HttpServletRequest request, HttpServletResponse response) throws IOException {



        //Gets email/username from authenticated user
        String email= principal.getName();

        //Loads userdetails and generates a token
        UserDetails userDetails= customUserDetailsService.loadUserByUsername(email);
        String jwtToken=jwtUtils.generateToken(userDetails);

        //To identify header as jwtToken add Bearer
        String authHeader= "Bearer "+jwtToken;

        System.out.println("Generated JWT token: "+authHeader);

        //Creating header named Authorization
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("Authorization",authHeader);


        PrintWriter out=response.getWriter();
        out.println(authHeader);

        return new ResponseEntity<>(httpHeaders,HttpStatus.OK);

    }


}
