package org.a3.mandarin.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table
public class DeletingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    @JsonIgnore
    private User librarian;

    @OneToOne
    @JoinColumn(name = "book_id", referencedColumnName = "bookId")
    private Book book;

    @Column
    private Instant deletingTime;

    public DeletingHistory(){}

    public DeletingHistory(Book book, User librarian, Instant deletingTime) {
        this.librarian = librarian;
        this.book = book;
        this.deletingTime = deletingTime;
    }

    public Integer getId() {
        return id;
    }

    public User getLibrarian() {
        return librarian;
    }

    public void setLibrarian(User librarian) {
        this.librarian = librarian;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Instant getDeletingTime() {
        return deletingTime;
    }

    public void setDeletingTime(Instant deletingTime) {
        this.deletingTime = deletingTime;
    }
}
