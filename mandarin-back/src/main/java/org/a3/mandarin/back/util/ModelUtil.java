package org.a3.mandarin.back.util;

import org.a3.mandarin.back.model.BookDescriptionModel;
import org.a3.mandarin.back.model.BookModel;
import org.a3.mandarin.common.dao.repository.BookRepository;
import org.a3.mandarin.common.entity.Book;
import org.a3.mandarin.common.entity.BookDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelUtil {
    private static BookRepository bookRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public void setBookRepository(BookRepository bookRepository){
        ModelUtil.bookRepository=bookRepository;
    }

    public static BookDescriptionModel convertToBookDescriptionModel(BookDescription bookDescription){
        Integer copyNumber=bookRepository.findByBookDescription_ISBN(bookDescription.getISBN()).size();
        return new BookDescriptionModel(bookDescription, copyNumber);
    }

    public static BookModel convertToBookModel(Book book){
        Boolean isReserving=bookRepository.isOnReserving(book.getBookId());
        Boolean isBorrowing=bookRepository.isOnBorrowing(book.getBookId());
        Boolean isDeleted=bookRepository.isDeleted(book.getBookId());
        return new BookModel(book, isReserving,isBorrowing, isDeleted);
    }
}
