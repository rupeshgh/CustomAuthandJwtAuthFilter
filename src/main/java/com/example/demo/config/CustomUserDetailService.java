package com.example.demo.config;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;




@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    //Checks if user exist and sets userdetails if exists
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {


       User user= userRepository.findByEmail(email);
        System.out.println(user.getRole());

       if(user==null){
           throw new UsernameNotFoundException("No such user");
       }



        return new CustomUserDetails(user);
    }
}
