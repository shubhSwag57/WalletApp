package com.example.walletApplication.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.userdetails.User;
import java.util.Collections;

@Entity
@Table(name = "USERDB")
@EqualsAndHashCode(callSuper = false)
public class Client extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String username;
    private  String password;
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private  Wallet wallet;

    public Client(String username, String password){
        super(username,password,Collections.emptyList());
        this.password = password;
    }

    public Client(String username, String password, Wallet wallet) {
        super(username, password, Collections.emptyList());
        this.password = password;
        this.wallet = wallet;
    }

    public Client() {
        super("defaultUsername", "defaultPassword", Collections.emptyList());
    }

    public boolean isValidPassword(String password){
        return this.password.equals(password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return username.equals(client.username) &&
                password.equals(client.password);
    }


//    public double getBalance() {
//        return wallet.checkBalance();
//    }
}
