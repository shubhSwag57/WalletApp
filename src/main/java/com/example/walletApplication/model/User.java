package com.example.walletApplication.model;

public class User {
    private final String username;
    private final String password;
    private final Wallet wallet;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.wallet = new Wallet();
    }

    public boolean isValidPassword(String password){
        return this.password.equals(password);
    }

    public void deposit(double amount){
        wallet.deposit(amount);
    }

    public void withdraw(double amount){
        wallet.withdraw(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username) &&
                password.equals(user.password);
    }


}
