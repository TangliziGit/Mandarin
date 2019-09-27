package org.a3.mandarin.common.dao.repository;

import org.a3.mandarin.common.entity.BookDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BookDescriptionQueryRepository extends JpaRepository<BookDescription, Integer>, QuerydslPredicateExecutor<BookDescription> {
}
