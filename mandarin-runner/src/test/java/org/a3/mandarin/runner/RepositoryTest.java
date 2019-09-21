package org.a3.mandarin.runner;

import org.a3.mandarin.common.entity.BorrowingFineHistory;
import org.junit.Assert;
import org.junit.Test;

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
        List<BorrowingFineHistory> borrowingFineHistories=borrowingFineHistoryRepository.findByUserId(3);

        System.out.println(borrowingFineHistories);
    }
}
