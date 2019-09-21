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

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "isbn", referencedColumnName = "ISBN")
    private BookDescription bookDescription;

    @OneToMany(mappedBy = "book")
    @OrderBy("reservingStartTime DESC")
    @JsonIgnore
    private List<ReservingHistory> reservingHistories=new ArrayList<>();

    @OneToOne(mappedBy = "book")
    @JsonIgnore
    private DeletingHistory deletingHistory;

    @OneToMany(mappedBy = "book")
    @OrderBy("borrowingStartTime DESC")
    @JsonIgnore
    private List<BorrowingHistory> borrowingHistories=new ArrayList<>();

    public Book() {}

    public Book(BookDescription bookDescription) {
        this.bookDescription = bookDescription;
    }

    public BookDescription getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(BookDescription bookDescription) {
        this.bookDescription = bookDescription;
    }

    public DeletingHistory getDeletingHistory() {
        return deletingHistory;
    }

    public void setDeletingHistory(DeletingHistory deletingHistory) {
        this.deletingHistory = deletingHistory;
    }

    public List<BorrowingHistory> getBorrowingHistories() {
        return borrowingHistories;
    }

    public void setBorrowingHistories(List<BorrowingHistory> borrowingHistories) {
        this.borrowingHistories = borrowingHistories;
    }

    public List<ReservingHistory> getReservingHistories() {
        return reservingHistories;
    }

    public void setReservingHistories(List<ReservingHistory> reservingHistories) {
        this.reservingHistories = reservingHistories;
    }

    public Integer getBookId() {
        return bookId;
    }

}
