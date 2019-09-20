package org.a3.mandarin.common.dao.repository;

import org.a3.mandarin.common.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryRepository extends JpaRepository<Category, Integer> , JpaSpecificationExecutor<Category> {
}
