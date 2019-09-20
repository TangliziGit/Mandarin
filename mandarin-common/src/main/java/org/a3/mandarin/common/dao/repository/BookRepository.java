package org.a3.mandarin.common.dao.repository;

import org.a3.mandarin.common.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {
    @Query("select case when count(dh)>=1 then true else false end from DeletingHistory dh inner join Book b on b=dh.book where b.bookId=?1")
    Boolean isDeleted(Integer bookId);

    @Query("select case when count(rh)>=1 then true else false end from ReservingHistory rh inner join Book b on b=rh.book where b.bookId=?1")
    Boolean isReserved(Integer bookId);
}
