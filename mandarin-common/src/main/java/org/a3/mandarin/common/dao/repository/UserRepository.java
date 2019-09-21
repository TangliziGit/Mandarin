package org.a3.mandarin.common.dao.repository;

import org.a3.mandarin.common.entity.BorrowingFineHistory;
import org.a3.mandarin.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    User findByName(String name);
    User findByPhoneNumber(String phoneNumber);
    User findByEmail(String email);

}
