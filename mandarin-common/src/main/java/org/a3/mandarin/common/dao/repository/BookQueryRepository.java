package org.a3.mandarin.common.dao.repository;

import org.a3.mandarin.common.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BookQueryRepository extends JpaRepository<Book, Integer>, QuerydslPredicateExecutor<Book> {
}
