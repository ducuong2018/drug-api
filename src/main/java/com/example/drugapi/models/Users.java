package com.example.drugapi.models;

import lombok.Data;
import lombok.extern.java.Log;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Table(name = "users")
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    private String name;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;
}
