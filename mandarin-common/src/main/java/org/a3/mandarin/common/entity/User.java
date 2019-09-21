package org.a3.mandarin.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Column(columnDefinition = "VARCHAR(40)", nullable = false)
    @JsonIgnore
    private String passwordHash;

    @Column(nullable = false)
    private Instant signupTime;

    @OneToMany(mappedBy = "reader")
    @OrderBy("reservingStartTime DESC")
    @JsonIgnore
    private List<ReservingHistory> reservingHistories;

    @OneToMany(mappedBy = "librarian")
    @OrderBy("deletingTime DESC")
    @JsonIgnore
    private List<DeletingHistory> deletingHistories;

    @OneToMany(mappedBy = "reader")
    @OrderBy("borrowingStartTime DESC")
    @JsonIgnore
    private List<BorrowingHistory> borrowingHistories;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "roleId"))
    private Set<Role> roles=new HashSet<>();

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

    public List<DeletingHistory> getDeletingHistories() {
        return deletingHistories;
    }

    public void setDeletingHistories(List<DeletingHistory> deletingHistories) {
        this.deletingHistories = deletingHistories;
    }

    public List<BorrowingHistory> getBorrowingHistories() {
        return borrowingHistories;
    }

    public void setBorrowingHistories(List<BorrowingHistory> borrowingHistories) {
        this.borrowingHistories = borrowingHistories;
    }

    public List<ReservingHistory> getReservingHistories() {
        return reservingHistories;
    }

    public void setReservingHistories(List<ReservingHistory> reservingHistories) {
        this.reservingHistories = reservingHistories;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Boolean verifyPassword(String password){
        return this.passwordHash.equals(password);
    }

    public void changePassword(String password){
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
