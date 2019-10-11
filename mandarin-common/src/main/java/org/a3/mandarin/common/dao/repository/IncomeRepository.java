package org.a3.mandarin.common.dao.repository;

import org.a3.mandarin.common.dto.IncomeSummary;
import org.a3.mandarin.common.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Integer>, JpaSpecificationExecutor<Income> {
}
