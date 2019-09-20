package org.a3.mandarin.runner;

import org.junit.Assert;
import org.junit.Test;

public class RepositoryTest extends MandarinRunnerApplicationTests{
    @Test
    public void testBook(){
        Boolean book1deleted=bookRepository.isDeleted(1);
        Boolean book1reserved=bookRepository.isReserved(1);
        Boolean book2deleted=bookRepository.isDeleted(2);
        Boolean book2reserved=bookRepository.isReserved(2);

        Assert.assertTrue(book1deleted);
        Assert.assertTrue(book1reserved);
        Assert.assertFalse(book2deleted);
        Assert.assertFalse(book2reserved);
    }
}
