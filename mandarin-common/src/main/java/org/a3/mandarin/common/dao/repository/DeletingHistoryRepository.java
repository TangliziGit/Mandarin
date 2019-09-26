package org.a3.mandarin.common.dao.repository;

import org.a3.mandarin.common.entity.DeletingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DeletingHistoryRepository extends JpaRepository<DeletingHistory, Integer>, JpaSpecificationExecutor<DeletingHistory> {
    List<DeletingHistory> findByLibrarian_UserId(Integer userId);
}
