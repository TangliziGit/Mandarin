package org.a3.mandarin.common.aop;

import org.a3.mandarin.common.annotation.Permission;
import org.a3.mandarin.common.entity.User;
import org.a3.mandarin.common.enums.PermissionType;
import org.a3.mandarin.common.enums.RoleType;
import org.a3.mandarin.common.util.UserUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

public abstract class AbstractPermissonAspect {

    @Pointcut("@annotation(permission)")
    public void authorize(Permission permission){}

    public Boolean checkPermission(List<PermissionType> permissionTypes, Integer userId){
        List<RoleType> roles= UserUtil.getRolesByUserId(userId);

        // TODO: ugly code
        for (RoleType roleType: roles) {
            for (PermissionType permissionType: permissionTypes)
                if (roleType.toString().equals(permissionType.toString()))
                    return true;
        }
        return false;
    }
}
