package org.a3.mandarin.runner;

import org.a3.mandarin.common.dao.query.UserQuery;
import org.a3.mandarin.common.entity.Book;
import org.a3.mandarin.common.entity.BorrowingFineHistory;
import org.a3.mandarin.common.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class RepositoryTest extends MandarinRunnerApplicationTests{
    @Test
    public void testBook(){
        Boolean book1deleted=bookRepository.isDeleted(1);
        Boolean book1onReserving=bookRepository.isOnReserving(1);
        Boolean book1onBorrowing=bookRepository.isOnBorrowing(1);

        Boolean book2deleted=bookRepository.isDeleted(2);
        Boolean book2onReserving=bookRepository.isOnReserving(2);
        Boolean book2onBorrowing=bookRepository.isOnBorrowing(2);

        Assert.assertTrue(book1deleted);
        Assert.assertTrue(book1onReserving);
        Assert.assertTrue(book1onBorrowing);
        Assert.assertFalse(book2deleted);
        Assert.assertFalse(book2onReserving);
        Assert.assertFalse(book2onBorrowing);
    }

    @Test
    public void testBorrowingFine(){
        List<BorrowingFineHistory> borrowingFineHistories=borrowingFineHistoryRepository.findBorrowingFineHistoriesByUserId(3);

        System.out.println(borrowingFineHistories);
    }

    @Test
    public void testFindReservingBooksByUserId(){
        List<Book> books = bookRepository.findReservingBooksByUserId(3);

        Assert.assertTrue(books.size() != 0);
        Assert.assertEquals((int)books.get(0).getBookId(), 1);
    }

    @Test
    public void testFindBorrowingBooksByUserId(){
        List<Book> books = bookRepository.findBorrowingBooksByUserId(3);

        Assert.assertTrue(books.size() != 0);
        Assert.assertEquals((int)books.get(0).getBookId(), 1);
    }

    @Test
    public void testFindReaders(){
        List<User> readers=userRepository.findReadersWithSpec(null, null);

        Assert.assertEquals((int)readers.size(), 2);
    }

    @Test
    public void testFindReadersWithSpec(){
        UserQuery userQuery=new UserQuery(){{
            setUserIdEqual(3);
        }};
        List<User> readers=userRepository.findReadersWithSpec(userQuery.toSpec(), PageRequest.of(0, 20));

        Assert.assertEquals((int)readers.size(), 2);
    }
}
