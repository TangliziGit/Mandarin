package org.a3.mandarin.common.dao.repository;

import org.a3.mandarin.common.entity.BorrowingFineHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BorrowingFineHistoryRepository extends JpaRepository<BorrowingFineHistory, Integer>, JpaSpecificationExecutor<BorrowingFineHistory> {
    @Query(value = "select bfh.* " +
            "from borrowing_fine_history bfh inner join borrowing_history bh on bfh.borrowing_history_id = bh.id " +
            "inner join user u on bh.user_id = u.user_id " +
            "where u.user_id=?1", nativeQuery = true)
    List<BorrowingFineHistory> findBorrowingFineHistoriesByUserId(Integer userId);

    // TODO: setting for fine per day
    @Query(value = "select database()", nativeQuery = true)
    Integer findTotalFineAmountByUserId(Integer userId);
}
