package org.a3.mandarin.common.dao.repository;

import org.a3.mandarin.common.entity.ReservingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReservationHistoryRepository extends JpaRepository<ReservingHistory, Integer>, JpaSpecificationExecutor<ReservingHistory> {
}
