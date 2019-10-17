package org.a3.mandarin.front.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.a3.mandarin.common.entity.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class BookModel {
    private Integer bookId;
    private Boolean deleted;
    private Boolean borrowed;
    private Boolean reserved;
    private BookDescription bookDescription;
    private List<ReservingHistory> reservingHistories=new ArrayList<>();
    private DeletingHistory deletingHistory;
    private List<BorrowingHistory> borrowingHistories=new ArrayList<>();

    public BookModel(Book book, Boolean deleted, Boolean borrowed, Boolean reserved){
        this.bookId = book.getBookId();
        this.deleted = deleted;
        this.borrowed = borrowed;
        this.reserved = reserved;
        this.bookDescription = book.getBookDescription();
        this.reservingHistories = book.getReservingHistories();
        this.deletingHistory = book.getDeletingHistory();
        this.borrowingHistories = book.getBorrowingHistories();
    }

    @Override
    public String toString() {
        return "BookModel{" +
                "bookId=" + bookId +
                ", deleted=" + deleted +
                ", borrowed=" + borrowed +
                ", reserved=" + reserved +
                ", bookDescription=" + bookDescription +
                ", reservingHistories=" + reservingHistories +
                ", deletingHistory=" + deletingHistory +
                ", borrowingHistories=" + borrowingHistories +
                '}';
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(Boolean borrowed) {
        this.borrowed = borrowed;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public BookDescription getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(BookDescription bookDescription) {
        this.bookDescription = bookDescription;
    }

    public List<ReservingHistory> getReservingHistories() {
        return reservingHistories;
    }

    public void setReservingHistories(List<ReservingHistory> reservingHistories) {
        this.reservingHistories = reservingHistories;
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
}
