package org.a3.mandarin.runner;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.a3.mandarin.common.entity.Book;
import org.a3.mandarin.common.entity.BorrowingFineHistory;
import org.a3.mandarin.common.entity.QUser;
import org.a3.mandarin.common.entity.User;
import org.a3.mandarin.common.util.RoleUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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
        Assert.assertEquals(1, (int)books.get(0).getBookId());
    }

    @Test
    public void testFindBorrowingBooksByUserId(){
        List<Book> books = bookRepository.findBorrowingBooksByUserId(3);

        Assert.assertTrue(books.size() != 0);
        Assert.assertEquals(1, (int)books.get(0).getBookId());
    }

    @Test
    public void testFindReaders(){
        List<User> readers=userRepository.findReadersWithSpec(null, null);

        Assert.assertEquals(2, readers.size());
    }

    @Test
    public void testFindReadersWithSpec(){
        QUser qUser=QUser.user;
        // Predicate predicate=qUser.roles.contains(RoleUtil.readerRole)
        //         .and(qUser.userId.eq(3));

        PageRequest pageRequest=PageRequest.of(0, 20, Sort.by("signUpTime"));
        BooleanExpression expression=qUser.roles.contains(RoleUtil.readerRole);
        expression=expression.and(qUser.userId.eq(3));

        Page<User> page=userQueryRepository.findAll(expression, pageRequest);
        System.out.println(RoleUtil.readerRole);

        Assert.assertEquals(1, page.getContent().size());
    }
}
