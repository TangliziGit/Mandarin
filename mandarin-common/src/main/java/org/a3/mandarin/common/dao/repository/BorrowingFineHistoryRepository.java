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

    // attend to this sql, it may return null
    @Query(value = "select ifnull(sum(datediff(now(), find_start_time)*(select value from setting where name='FINE')), 0) " +
            "from user u inner join borrowing_history bh on u.user_id = bh.user_id " +
            "inner join borrowing_fine_history bfh on bh.id = bfh.borrowing_history_id " +
            "where u.user_id=?1 and bfh.paid=false;", nativeQuery = true)
    Integer findTotalFineAmountByUserId(Integer userId);

    @Query(value = "select datediff(now(), find_start_time)*(select value from setting where name='FINE') " +
            "from borrowing_fine_history bfh " +
            "where bfh.fine_id=?1", nativeQuery = true)
    Double findFineValuetByBorrowingFineHistoryId(Integer id);
}
