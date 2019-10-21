package org.a3.mandarin.common.dao.repository;

import org.a3.mandarin.common.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NewsRepository extends JpaRepository<News, Integer>, JpaSpecificationExecutor<News> {

}
