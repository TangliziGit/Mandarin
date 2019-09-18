package org.a3.mandarin.common.aop.dao.repository;

import org.a3.mandarin.common.entity.User;
import org.a3.mandarin.common.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    @Query(value = "select role_name from user, user_role, role where user.user_id=1 and user.user_id=user_role.user_id and user_role.role_id=role.role_id;", nativeQuery = true)
    List<RoleType> findRolesByUserId(Integer userId);

    @Modifying
    @Query(value = "insert into user_role(user_id, role_id) values(?1, (select role_id from role where role.role_name=?2))", nativeQuery = true)
    void saveRoleByUserId(Integer userId, String roleName);

    @Query(value = "select case when count(role.role_id)>0 then true else false end from user_role, role where user_role.user_id=?1 and role.role_id=user_role.role_id and role.role_name=?2", nativeQuery = true)
    int checkUserRoleByUserId(Integer userId, String roleName);

    User findByName(String name);
    User findByPhoneNumber(String phoneNumber);
    User findByEmail(String email);
}
