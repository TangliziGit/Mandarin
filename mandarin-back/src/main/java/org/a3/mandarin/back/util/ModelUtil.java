package org.a3.mandarin.back.util;

import org.a3.mandarin.back.model.BookDescriptionModel;
import org.a3.mandarin.back.model.BookModel;
import org.a3.mandarin.back.model.BorrowingHistoryModel;
import org.a3.mandarin.common.dao.repository.BookRepository;
import org.a3.mandarin.common.dao.repository.BorrowingFineHistoryRepository;
import org.a3.mandarin.common.dao.repository.SettingRepository;
import org.a3.mandarin.common.entity.Book;
import org.a3.mandarin.common.entity.BookDescription;
import org.a3.mandarin.common.entity.BorrowingFineHistory;
import org.a3.mandarin.common.entity.BorrowingHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelUtil {
    private static BookRepository bookRepository;
    private static BorrowingFineHistoryRepository borrowingFineHistoryRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public void setBookRepository(BookRepository bookRepository){
        ModelUtil.bookRepository=bookRepository;
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public void setBorrowingFineHistoryRepository(BorrowingFineHistoryRepository borrowingFineHistoryRepository){
        ModelUtil.borrowingFineHistoryRepository=borrowingFineHistoryRepository;
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

    public static BorrowingHistoryModel convertToBorrowingHistoryModel(BorrowingHistory borrowingHistory){
        Double fine=0.0;
        BorrowingFineHistory borrowingFineHistory = borrowingHistory.getBorrowingFineHistory();
        if (borrowingFineHistory != null)
            fine=borrowingFineHistoryRepository.findFineValuetByBorrowingFineHistoryId(borrowingFineHistory.getFineId());
        return new BorrowingHistoryModel(borrowingHistory, fine);
    }
}
