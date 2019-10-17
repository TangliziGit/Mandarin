package org.a3.mandarin.common.dao.repository;

import org.a3.mandarin.common.entity.BookDescription;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BookDescriptionRepository extends JpaRepository<BookDescription, String>, JpaSpecificationExecutor<BookDescription> {
    @Query("select bd from BookDescription bd where bd.ISBN=?1")
    BookDescription findByISBN(String isbn);
}
