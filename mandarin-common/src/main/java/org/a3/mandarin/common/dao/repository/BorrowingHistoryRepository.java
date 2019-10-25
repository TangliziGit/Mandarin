package org.a3.mandarin.common.dao.repository;

import org.a3.mandarin.common.entity.BorrowingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BorrowingHistoryRepository extends JpaRepository<BorrowingHistory, Integer>, JpaSpecificationExecutor<BorrowingHistory> {
    @Query(value = "select * " +
            "from borrowing_history " +
            "where borrowing_end_time is null", nativeQuery = true)
    List<BorrowingHistory> findNoReturnHistory();
}
