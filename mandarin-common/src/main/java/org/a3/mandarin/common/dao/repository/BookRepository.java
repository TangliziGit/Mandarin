package org.a3.mandarin.common.dao.repository;

import org.a3.mandarin.common.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {
    @Query("select case when count(dh)>=1 then true else false end " +
            "from DeletingHistory dh inner join Book b on b=dh.book where b.bookId=?1")
    Boolean isDeleted(Integer bookId);

    @Query(value = "select case when count(*)>=1 then 'true' else 'false' end " +
            "from reserving_history rh inner join book b on b.book_id=rh.book_id " +
            "where b.book_id=?1 and now() < date_add(rh.reserving_start_time, interval 2 hour) and rh.fetched = false", nativeQuery = true)
    Boolean isOnReserving(Integer bookId);

    @Query(value = "select case when count(*)>=1 then 'true' else 'false' end " +
            "from borrowing_history bh inner join book b on bh.book_id = b.book_id " +
            "where b.book_id=?1 and bh.borrowing_end_time is null", nativeQuery = true)
    Boolean isOnBorrowing(Integer bookId);

    @Query(value = "select b.* " +
            "from reserving_history rh inner join user u on u.user_id=rh.reader_id " +
            "inner join book b on rh.book_id = b.book_id " +
            "where u.user_id=?1 " +
            "  and now() < date_add(rh.reserving_start_time, interval 2 hour) " +
            "  and rh.fetched = false", nativeQuery = true)
    List<Book> findReservingBooksByUserId(Integer userId);

    @Query(value = "select b.* " +
            "from borrowing_history bh inner join book b on bh.book_id = b.book_id " +
            "inner join user u on bh.user_id = u.user_id " +
            "where u.user_id=?1 " +
            "  and bh.borrowing_end_time is null", nativeQuery = true)
    List<Book> findBorrowingBooksByUserId(Integer userId);
}
