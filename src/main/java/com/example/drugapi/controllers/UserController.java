package com.example.drugapi.controllers;

import com.example.drugapi.Dtos.LoginDto;
import com.example.drugapi.Dtos.RegisterDto;
import com.example.drugapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/v1")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto) throws IOException {
        return userService.addUser(registerDto);
    }
    @PostMapping("/login")
    public  ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) throws IOException {
        return  userService.createToken(loginDto);
    }
}
