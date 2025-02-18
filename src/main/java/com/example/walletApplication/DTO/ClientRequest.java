package com.example.walletApplication.DTO;

public class ClientRequest {
    private String username;
    private String password;

    // Getters and setters
    public ClientRequest(String username,String password){
        this.password = password;
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
