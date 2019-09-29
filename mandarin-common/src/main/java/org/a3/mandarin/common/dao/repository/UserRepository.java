package org.a3.mandarin.common.dao.repository;

import org.a3.mandarin.common.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    User findByName(String name);
    User findByPhoneNumber(String phoneNumber);
    User findByEmail(String email);

    @Query(value = "select u.* " +
            "from user u inner join user_role ur on u.user_id = ur.user_id " +
            "inner join role r on ur.role_id = r.role_id " +
            "where r.role_name='READER'", nativeQuery = true)
    List<User> findReadersWithSpec(Specification specification, PageRequest pageRequest);

    @Query(value = "select u.* " +
            "from user u inner join user_role ur on u.user_id = ur.user_id " +
            "inner join role r on ur.role_id = r.role_id " +
            "where r.role_name='LIBRARIAN'", nativeQuery = true)
    List<User> findLibrariansWithSpec(Specification specification, PageRequest pageRequest);

    @Query(value = "select u.* " +
            "from user u inner join user_role ur on u.user_id = ur.user_id " +
            "inner join role r on ur.role_id = r.role_id " +
            "where r.role_name='ADMIN'", nativeQuery = true)
    List<User> findAdminsWithSpec(Specification specification, PageRequest pageRequest);
}
