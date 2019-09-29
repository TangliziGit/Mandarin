package org.a3.mandarin.common.dao.repository;

import org.a3.mandarin.common.entity.ReservingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ReservationHistoryRepository extends JpaRepository<ReservingHistory, Integer>, JpaSpecificationExecutor<ReservingHistory> {
    @Query(value = "select case when count(*)>=1 then 'true' else 'false' end " +
            "from reserving_history rh " +
            "where rh.id=?1 and now() >= date_add(rh.reserving_start_time, interval 2 hour) and rh.fetched = false", nativeQuery = true)
    Boolean isReservingFailed(Integer id);

    @Query(value = "select case when count(*)>=1 then 'true' else 'false' end " +
            "from reserving_history rh " +
            "where rh.id=?1 and rh.fetched = true", nativeQuery = true)
    Boolean isReservingSucceeded(Integer id);
}
