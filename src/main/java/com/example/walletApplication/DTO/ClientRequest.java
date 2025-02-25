package com.example.walletApplication.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {
    @NotNull(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")

    private String username;
    @NotNull(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    
    private String password;



}
