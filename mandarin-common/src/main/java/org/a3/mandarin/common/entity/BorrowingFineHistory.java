package org.a3.mandarin.common.entity;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table
public class BorrowingFineHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fineId;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private Boolean paid;

    @Column(nullable = false)
    private Instant findStartTime;

    @Column
    private Instant findEndTime;

    @OneToOne
    @JoinColumn(name = "borrowing_history_id", referencedColumnName = "id")
    private BorrowingHistory borrowingHistory;

    public BorrowingFineHistory() {}

    public BorrowingFineHistory(Integer amount, Instant findStartTime) {
        this.amount = amount;
        this.findStartTime = findStartTime;
        this.findEndTime = null;
        this.paid = false;
    }

    public Integer getFineId() {
        return fineId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Instant getFindStartTime() {
        return findStartTime;
    }

    public void setFindStartTime(Instant findStartTime) {
        this.findStartTime = findStartTime;
    }

    public Instant getFindEndTime() {
        return findEndTime;
    }

    public void setFindEndTime(Instant findEndTime) {
        this.findEndTime = findEndTime;
    }

    public BorrowingHistory getBorrowingHistory() {
        return borrowingHistory;
    }

    public void setBorrowingHistory(BorrowingHistory borrowingHistory) {
        this.borrowingHistory = borrowingHistory;
    }
}
