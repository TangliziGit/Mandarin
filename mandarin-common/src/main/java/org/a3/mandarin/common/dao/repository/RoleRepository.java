package org.a3.mandarin.common.dao.repository;

import org.a3.mandarin.common.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {
    Role findByRoleName(String name);
}
