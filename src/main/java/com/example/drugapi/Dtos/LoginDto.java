package com.example.drugapi.Dtos;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class LoginDto {
    @Email
    private String email;
    @NotNull
    private String password;
}
