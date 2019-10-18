package org.a3.mandarin.back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.a3.mandarin.common.entity.Book;
import org.a3.mandarin.common.entity.BorrowingFineHistory;
import org.a3.mandarin.common.entity.BorrowingHistory;
import org.a3.mandarin.common.entity.User;

import javax.persistence.*;
import java.time.Instant;

public class BorrowingHistoryModel {
    private Integer id;
    private Book book;
    private Instant borrowingStartTime;
    private Instant borrowingEndTime;
    private String fineStatus;
    private String returnStatus;

    public BorrowingHistoryModel(BorrowingHistory borrowingHistory, Double fine){
        this.id = borrowingHistory.getId();
        this.book = borrowingHistory.getBook();
        this.borrowingStartTime = borrowingHistory.getBorrowingStartTime();
        this.borrowingEndTime = borrowingHistory.getBorrowingEndTime();

        if (borrowingHistory.getBorrowingFineHistory() == null)
            this.fineStatus = "no fine";
        else if (borrowingHistory.getBorrowingFineHistory().getPaid())
            this.fineStatus = "paid $"+fine;
        else
            this.fineStatus = "unpaid $"+fine;

        if (this.borrowingEndTime == null)
            this.returnStatus = "borrowing";
        else this.returnStatus = "returned";
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Instant getBorrowingStartTime() {
        return borrowingStartTime;
    }

    public void setBorrowingStartTime(Instant borrowingStartTime) {
        this.borrowingStartTime = borrowingStartTime;
    }

    public Instant getBorrowingEndTime() {
        return borrowingEndTime;
    }

    public void setBorrowingEndTime(Instant borrowingEndTime) {
        this.borrowingEndTime = borrowingEndTime;
    }

    public String getFineStatus() {
        return fineStatus;
    }

    public void setFineStatus(String fineStatus) {
        this.fineStatus = fineStatus;
    }
}
