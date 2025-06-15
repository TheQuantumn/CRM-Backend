package com.example.cmr.dto; // or your actual package

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RegisterRequest {
    // Getters and setters
    private String username;
    private String password;
    private String role;

    // Add other fields like email, name, etc. if needed

}
