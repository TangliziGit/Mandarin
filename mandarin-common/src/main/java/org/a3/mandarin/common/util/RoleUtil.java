package org.a3.mandarin.common.util;

import org.a3.mandarin.common.dao.repository.RoleRepository;
import org.a3.mandarin.common.entity.Role;
import org.a3.mandarin.common.enums.PermissionType;
import org.a3.mandarin.common.enums.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleUtil {
    private static RoleRepository roleRepository;
    public static Role readerRole, librarianRole, adminRole;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository){
        RoleUtil.roleRepository=roleRepository;
    }

    public static void initRoles(){
        RoleUtil.readerRole=RoleUtil.roleRepository.findByRoleName(RoleType.READER.toString());
        RoleUtil.librarianRole=RoleUtil.roleRepository.findByRoleName(RoleType.LIBRARIAN.toString());
        RoleUtil.adminRole=RoleUtil.roleRepository.findByRoleName(RoleType.ADMIN.toString());
    }

    public static Role convertPermissionTypeToRole(PermissionType permissionType){
        if (null==readerRole || null==librarianRole || null==adminRole)
            initRoles();

        if (permissionType==PermissionType.READER)
            return readerRole;
        if (permissionType==PermissionType.LIBRARIAN)
            return librarianRole;
        if (permissionType==PermissionType.ADMIN)
            return adminRole;
        return null;
    }
}
