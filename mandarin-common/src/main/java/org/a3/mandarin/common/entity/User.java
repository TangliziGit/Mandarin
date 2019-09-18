package org.a3.mandarin.common.entity;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(columnDefinition = "VARCHAR(20)", nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "VARCHAR(20)", nullable = false, unique = true)
    private String phoneNumber;

    @Column(columnDefinition = "VARCHAR(40)", nullable = false, unique = true)
    private String email;

    @Column(columnDefinition = "VARCHAR(40)", nullable = false, unique = true)
    private String passwordHash;

    @Column(nullable = false)
    private Instant signupTime;

    public User() {}

    public User(String name, String phoneNumber, String email, Instant signupTime, String password) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.signupTime = signupTime;
        this.changePassword(password);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", signupTime=" + signupTime +
                '}';
    }

    public Boolean verifyPassword(String password){
        // TODO
        return this.passwordHash.equals(password);
    }

    public void changePassword(String password){
        // TODO
        this.passwordHash=password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Instant getSignupTime() {
        return signupTime;
    }
}
