package org.a3.mandarin.common.dao.repository;

import org.a3.mandarin.common.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface LocationQueryRepository extends JpaRepository<Location, Integer>,
        JpaSpecificationExecutor<Location>, QuerydslPredicateExecutor<Location> {
}
