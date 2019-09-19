package org.a3.mandarin.common.aop;

import org.a3.mandarin.common.annotation.Permission;
import org.a3.mandarin.common.entity.Role;
import org.a3.mandarin.common.entity.User;
import org.a3.mandarin.common.enums.PermissionType;
import org.a3.mandarin.common.util.RoleUtil;
import org.aspectj.lang.annotation.Pointcut;

import java.util.List;
import java.util.Set;

public abstract class AbstractPermissonAspect {

    @Pointcut("@annotation(permission)")
    public void authorize(Permission permission){}

    protected Boolean checkPermission(List<PermissionType> permissionTypes, User user){
        for (PermissionType permissionType: permissionTypes) {
            System.out.println(RoleUtil.convertPermissionTypeToRole(permissionType));
            if (user.getRoles().contains(RoleUtil.convertPermissionTypeToRole(permissionType)))
                return true;
        }
        return false;
    }
}
