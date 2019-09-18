package org.a3.mandarin.common.util;

import org.a3.mandarin.common.aop.dao.repository.UserRepository;
import org.a3.mandarin.common.enums.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserUtil {
    private static int FALSE =0, TRUE=1;
    private static UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository){
        UserUtil.userRepository=userRepository;
    }

    public static void setRoleByUserId(Integer userId, RoleType roleType){
        if (FALSE == userRepository.checkUserRoleByUserId(userId, roleType.toString()))
            userRepository.saveRoleByUserId(userId, roleType.toString());
    }

    public static void setRolesByUserId(Integer userId, List<RoleType> roleTypes){
        // TODO
        for (RoleType roleType : roleTypes)
            if (FALSE == userRepository.checkUserRoleByUserId(userId, roleType.toString())){
                userRepository.saveRoleByUserId(userId, roleType.toString());
            }
    }

    public static List<RoleType> getRolesByUserId(Integer userId){
        return userRepository.findRolesByUserId(userId);
    }
}
