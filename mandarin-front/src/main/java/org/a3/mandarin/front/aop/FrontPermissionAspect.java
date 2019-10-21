package org.a3.mandarin.front.aop;

import org.a3.mandarin.common.annotation.Permission;
import org.a3.mandarin.common.aop.AbstractPermissionAspect;
import org.a3.mandarin.common.dao.repository.UserRepository;
import org.a3.mandarin.common.entity.User;
import org.a3.mandarin.common.enums.PermissionType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class FrontPermissionAspect extends AbstractPermissionAspect {
    private final Logger logger= LoggerFactory.getLogger(FrontPermissionAspect.class);

    @Resource
    private UserRepository userRepository;

    @Pointcut("execution(public * org.a3.mandarin.front.controller.AdminManagementController.*(..))")
    public void frontAdminPointcut(){}

    // @Around(value = "authorize(permission) && frontAdminPointcut()", argNames = "joinPoint,permission")
    public Object adminDoAround(ProceedingJoinPoint joinPoint, Permission permission) throws Throwable{
        ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == attributes)
            return "admin/404";

        HttpServletRequest request=attributes.getRequest();
        List<PermissionType> permissionTypes= Arrays.asList(permission.value());

        if (null == request)
            return "admin/404";

        logger.info("CHECK PERMISSION: "+request.getRequestURL().toString());
        HttpSession session=request.getSession();
        if (null == session.getAttribute("userId"))
            return "admin/403";

        Integer userId=(Integer)session.getAttribute("userId");
        User user=userRepository.findById(userId).orElse(null);

        if (null == user)
            return "admin/404";

        logger.info("ROLE TYPE: "+ user.getRoles().toString());

        if (!checkPermission(permissionTypes, user))
            return "admin/403";

        logger.info("PERMISSION PASSED: "+request.getRequestURL().toString());
        return joinPoint.proceed();
    }

    @Pointcut("execution(public * org.a3.mandarin.front.controller.LibrarianManagementController.*(..))")
    public void frontLibrarianPointcut(){}

    // @Around(value = "authorize(permission) && frontLibrarianPointcut()", argNames = "joinPoint,permission")
    public Object librarianDoAround(ProceedingJoinPoint joinPoint, Permission permission) throws Throwable{
        ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String type="librarian";
        if (null == attributes)
            return type+"/404";

        HttpServletRequest request=attributes.getRequest();
        List<PermissionType> permissionTypes= Arrays.asList(permission.value());

        if (null == request)
            return type+"/404";

        logger.info("CHECK PERMISSION: "+request.getRequestURL().toString());
        HttpSession session=request.getSession();
        if (null == session.getAttribute("userId"))
            return type+"/403";

        Integer userId=(Integer)session.getAttribute("userId");
        User user=userRepository.findById(userId).orElse(null);

        if (null == user)
            return type+"/404";

        logger.info("ROLE TYPE: "+ user.getRoles().toString());

        if (!checkPermission(permissionTypes, user))
            return type+"/403";

        logger.info("PERMISSION PASSED: "+request.getRequestURL().toString());
        return joinPoint.proceed();
    }
}
