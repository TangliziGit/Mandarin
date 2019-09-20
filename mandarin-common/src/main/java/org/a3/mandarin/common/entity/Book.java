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
    private List<ReservationHistory> reservationHistories=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "book_description_isbn", referencedColumnName = "ISBN")
    private BookDescription bookDescription;

    public Book() {}

    public Book(String ISBN) {
        this.ISBN = ISBN;
    }

    public List<ReservationHistory> getReservationHistories() {
        return reservationHistories;
    }

    public void setReservationHistories(List<ReservationHistory> reservationHistories) {
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
