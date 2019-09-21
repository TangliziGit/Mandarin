package org.a3.mandarin.common.dao.repository;

import org.a3.mandarin.common.entity.BorrowingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BorrowingHistoryRepository extends JpaRepository<BorrowingHistory, Integer>, JpaSpecificationExecutor<BorrowingHistory> {
}
