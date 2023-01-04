package com.example.demo.service;


import com.example.demo.model.Roles;
import com.example.demo.model.User;
import com.example.demo.repository.RolesRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RolesRepository rolesRepository;

    public  void createUser(User user){

        userRepository.save(user);


    }

    public void saveRole(Roles roles){

        rolesRepository.save(roles);

    }

    public void addRoleToUser(String email,String Role){

        User user=userRepository.findByEmail(email);



        Roles role=rolesRepository.findByRoleName(Role);
        user.getRole().add(role);

        userRepository.save(user);
    }

}

