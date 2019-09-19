package org.a3.mandarin.common.aop.dao.repository;

import org.a3.mandarin.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    User findByName(String name);
    User findByPhoneNumber(String phoneNumber);
    User findByEmail(String email);
}
