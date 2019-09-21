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
public class ReservingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "bookId")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "reader_id", referencedColumnName = "userId")
    @JsonIgnore
    private User reader;

    @Column(nullable = false)
    private Instant reservingStartTime;

    @Column
    private Instant reservingEndTime;

    @Column(nullable = false)
    private Boolean fetched;

    public ReservingHistory() {}

    public ReservingHistory(Book book, User reader, Instant reservingStartTime, Instant reservingEndTime, Boolean fetched) {
        this.book = book;
        this.reader = reader;
        this.reservingStartTime = reservingStartTime;
        this.reservingEndTime = reservingEndTime;
        this.fetched = fetched;
    }

    public ReservingHistory(Book book, User reader, Instant reservingStartTime) {
        this.book = book;
        this.reader = reader;
        this.reservingStartTime = reservingStartTime;
        this.reservingEndTime = null;
        this.fetched = false;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Instant getReservingEndTime() {
        return reservingEndTime;
    }

    public void setReservingEndTime(Instant reservingEndTime) {
        this.reservingEndTime = reservingEndTime;
    }

    public Book getBook() {
        return book;
    }

    public Boolean getFetched() {
        return fetched;
    }

    public void setFetched(Boolean fetched) {
        this.fetched = fetched;
    }

    public User getReader() {
        return reader;
    }

    public void setReader(User reader) {
        this.reader = reader;
    }

    public Instant getReservingStartTime() {
        return reservingStartTime;
    }

    public void setReservingStartTime(Instant reservingStartTime) {
        this.reservingStartTime = reservingStartTime;
    }

}
