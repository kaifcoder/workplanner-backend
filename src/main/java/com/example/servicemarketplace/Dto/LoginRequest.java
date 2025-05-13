package com.example.servicemarketplace.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LoginRequest {
    public String username;
    public String password;
}
