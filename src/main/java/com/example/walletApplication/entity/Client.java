package com.example.walletApplication.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Entity
@Table(name = "\"client\"")
@EqualsAndHashCode(callSuper = false)
public class Client extends User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private  String username;
    @Column(name = "password")
    private  String password;
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private  Wallet wallet;

    public Client(String username, String password){
        super(username,password,Collections.emptyList());
        this.username = username;
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

    public boolean isValidPassword(String password, PasswordEncoder passwordEncoder){
        Boolean res  = passwordEncoder.matches(password, this.password);
        System.out.println(res);
        return res;

    }
    public boolean isSameUserName(String username){
        return this.username.equals(username);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return username.equals(client.username) &&
                password.equals(client.password);
    }

}
