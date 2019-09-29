package org.a3.mandarin.common.dao.repository;

import org.a3.mandarin.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserQueryRepository extends JpaRepository<User, Integer>, QuerydslPredicateExecutor<User> {
}
