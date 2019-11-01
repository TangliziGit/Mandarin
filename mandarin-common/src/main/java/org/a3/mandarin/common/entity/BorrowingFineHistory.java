package org.a3.mandarin.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table
public class BorrowingFineHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fineId;

    @Column(nullable = false)
    private Boolean paid = false;

    @Column(nullable = false)
    private Instant findStartTime;

    @Column
    private Instant findEndTime = null;

    @OneToOne
    @JoinColumn(name = "borrowing_history_id", referencedColumnName = "id")
    @JsonIgnore
    private BorrowingHistory borrowingHistory;

    public BorrowingFineHistory() {}

    public BorrowingFineHistory(Instant findStartTime) {
        this.findStartTime = findStartTime;
        this.findEndTime = null;
        this.paid = false;
    }

    public Integer getFineId() {
        return fineId;
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
