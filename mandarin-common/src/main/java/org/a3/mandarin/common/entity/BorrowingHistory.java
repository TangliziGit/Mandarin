package org.a3.mandarin.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.Instant;

// reserving state:
// 1. on reserving
//      now - startTime < 2 hour (endTime == null)
//      fetched == false
// 2. fail (over 2 hour)
//      now - startTime >= 2 hour (endTime != null)
//      fetched == false
// 3. success (fetched book)
//      startTime && endTime ?
//      fetched == true
// ! anyone can not cancel a reservation

@Entity
@Table
public class BorrowingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    @JsonIgnore
    private User reader;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "bookId")
    private Book book;

    @Column
    private Instant borrowingStartTime;

    @Column
    private Instant borrowingEndTime;

    @OneToOne(cascade = CascadeType.PERSIST)
    private BorrowingFineHistory borrowingFineHistory;

    public BorrowingHistory(){}

    public BorrowingHistory(Book book, User reader, Instant borrowingStartTime) {
        this.reader = reader;
        this.book = book;
        this.borrowingStartTime = borrowingStartTime;
        this.borrowingEndTime = null;
    }

    public BorrowingHistory(Book book, User reader, Instant borrowingStartTime, Instant borrowingEndTime) {
        this.reader = reader;
        this.book = book;
        this.borrowingStartTime = borrowingStartTime;
        this.borrowingEndTime = borrowingEndTime;
    }

    public BorrowingFineHistory getBorrowingFineHistory() {
        return borrowingFineHistory;
    }

    public void setBorrowingFineHistory(BorrowingFineHistory borrowingFineHistory) {
        this.borrowingFineHistory = borrowingFineHistory;
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

    public Integer getId() {
        return id;
    }

    public User getReader() {
        return reader;
    }

    public void setReader(User reader) {
        this.reader = reader;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
