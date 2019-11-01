package org.a3.mandarin.common.dao.repository;

import org.a3.mandarin.common.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface NewsQueryRepository extends JpaRepository<News, Integer>, QuerydslPredicateExecutor<News> {
}
