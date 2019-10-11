package org.a3.mandarin.common.entity;

import org.a3.mandarin.common.enums.IncomeType;

import javax.persistence.*;
import java.awt.event.WindowStateListener;
import java.time.Instant;

@Entity
@Table
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private String type;

    @Column(nullable = false)
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    @Column
    private Instant date;

    public Income() {
    }

    public Income(String type, Double amount, User user, Instant date) {
        this.type = type;
        this.amount = amount;
        this.user = user;
        this.date = date;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void changeType(IncomeType type) {
        this.type = type.name();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
