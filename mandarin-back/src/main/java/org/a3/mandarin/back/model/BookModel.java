package org.a3.mandarin.back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.a3.mandarin.common.dao.repository.BookRepository;
import org.a3.mandarin.common.entity.Book;
import org.a3.mandarin.common.entity.BookDescription;

import javax.annotation.Resource;

public class BookModel {
    @Resource
    @JsonIgnore
    private BookRepository bookRepository;

    private Integer bookId;
    private BookDescription bookDescription;
    private Boolean isReserving;
    private Boolean isBorrowing;
    private Boolean isDeleted;

    public BookModel(){}

    public BookModel(Book book){
        this.bookId = book.getBookId();
        this.bookDescription = book.getBookDescription();
        this.isReserving = bookRepository.isOnReserving(bookId);
        this.isBorrowing = bookRepository.isOnBorrowing(bookId);
        this.isDeleted = bookRepository.isDeleted(bookId);
    }

    public BookModel(Integer bookId, BookDescription bookDescription, Boolean isReserving, Boolean isBorrowing, Boolean isDeleted) {
        this.bookId = bookId;
        this.bookDescription = bookDescription;
        this.isReserving = isReserving;
        this.isBorrowing = isBorrowing;
        this.isDeleted = isDeleted;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public BookDescription getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(BookDescription bookDescription) {
        this.bookDescription = bookDescription;
    }

    public Boolean getReserving() {
        return isReserving;
    }

    public void setReserving(Boolean reserving) {
        isReserving = reserving;
    }

    public Boolean getBorrowing() {
        return isBorrowing;
    }

    public void setBorrowing(Boolean borrowing) {
        isBorrowing = borrowing;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
