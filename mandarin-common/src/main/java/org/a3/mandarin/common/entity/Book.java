package org.a3.mandarin.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;

    @Column(columnDefinition = "VARCHAR(30)", nullable = false)
    private String ISBN;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private List<ReservingHistory> reservationHistories=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "book_description_isbn", referencedColumnName = "ISBN")
    private BookDescription bookDescription;

    @OneToOne(mappedBy = "book")
    @JsonIgnore
    private DeletingHistory deletingHistory;

    public Book() {}

    public Book(String ISBN) {
        this.ISBN = ISBN;
    }

    public List<ReservingHistory> getReservationHistories() {
        return reservationHistories;
    }

    public void setReservationHistories(List<ReservingHistory> reservationHistories) {
        this.reservationHistories = reservationHistories;
    }

    public Integer getBookId() {
        return bookId;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
}
