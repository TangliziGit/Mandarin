package org.a3.mandarin.back.model;

import org.a3.mandarin.back.util.ModelUtil;
import org.a3.mandarin.common.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookModel {
    private Integer bookId;
    private BookDescriptionModel bookDescriptionModel;
    private Boolean isReserving;
    private Boolean isBorrowing;
    private Boolean isDeleted;

    public BookModel(){}

    public BookModel(Book book, Boolean isReserving, Boolean isBorrowing, Boolean isDeleted){
        this.bookId = book.getBookId();
        this.bookDescriptionModel = ModelUtil.convertToBookDescriptionModel(book.getBookDescription());
        this.isReserving = isReserving;
        this.isBorrowing = isBorrowing;
        this.isDeleted = isDeleted;
    }

    public BookModel(Integer bookId, BookDescriptionModel bookDescriptionModel, Boolean isReserving, Boolean isBorrowing, Boolean isDeleted) {
        this.bookId = bookId;
        this.bookDescriptionModel = bookDescriptionModel;
        this.isReserving = isReserving;
        this.isBorrowing = isBorrowing;
        this.isDeleted = isDeleted;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public BookDescriptionModel getBookDescriptionModel() {
        return bookDescriptionModel;
    }

    public void setBookDescriptionModel(BookDescriptionModel bookDescriptionModel) {
        this.bookDescriptionModel = bookDescriptionModel;
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
