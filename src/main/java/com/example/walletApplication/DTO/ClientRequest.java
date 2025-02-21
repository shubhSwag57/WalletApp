package com.example.walletApplication.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRequest {
    @NotNull(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")

    private String username;
    @NotNull(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")

    private String password;

    // Getters and setters
    public ClientRequest(String username,String password){
        this.password = password;
        this.username = username;
    }


}
