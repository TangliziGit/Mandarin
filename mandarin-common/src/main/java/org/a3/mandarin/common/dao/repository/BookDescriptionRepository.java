package org.a3.mandarin.common.dao.repository;

import org.a3.mandarin.common.entity.BookDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookDescriptionRepository extends JpaRepository<BookDescription, String>, JpaSpecificationExecutor<BookDescription> {
}
