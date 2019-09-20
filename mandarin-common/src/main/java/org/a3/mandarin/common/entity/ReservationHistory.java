package org.a3.mandarin.common.entity;


import javax.persistence.*;
import java.time.Instant;

@Entity
@Table
public class ReservationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "bookId")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "reader_id", referencedColumnName = "userId")
    private User reader;

    @Column(nullable = false)
    private Instant reserveTime;

    @Column(nullable = false)
    private Boolean fetched;

    public ReservationHistory() {}

    public ReservationHistory(Book book, User reader, Instant reserveTime, Boolean fetched) {
        this.book=book;
        this.reader = reader;
        this.reserveTime = reserveTime;
        this.fetched = fetched;
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

    public Instant getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(Instant reserveTime) {
        this.reserveTime = reserveTime;
    }

}
