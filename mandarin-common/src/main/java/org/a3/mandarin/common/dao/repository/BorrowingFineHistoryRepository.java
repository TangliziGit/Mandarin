package org.a3.mandarin.common.dao.repository;

import org.a3.mandarin.common.entity.BorrowingFineHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BorrowingFineHistoryRepository extends JpaRepository<BorrowingFineHistory, Integer>, JpaSpecificationExecutor<BorrowingFineHistory> {
}
