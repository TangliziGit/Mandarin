package org.a3.mandarin.common.entity;


import javax.persistence.*;
import java.time.Instant;

@Entity
@Table
public class ReservingHistory {
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
    private Instant reservingTime;

    @Column(nullable = false)
    private Boolean fetched;

    public ReservingHistory() {}

    public ReservingHistory(Book book, User reader, Instant reservingTime, Boolean fetched) {
        this.book=book;
        this.reader = reader;
        this.reservingTime = reservingTime;
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

    public Instant getReservingTime() {
        return reservingTime;
    }

    public void setReservingTime(Instant reservingTime) {
        this.reservingTime = reservingTime;
    }

}
