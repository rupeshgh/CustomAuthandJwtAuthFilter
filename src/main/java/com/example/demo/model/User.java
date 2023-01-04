package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String Username;
    private String password;

    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private  List<Roles>role=new ArrayList<>();


}
