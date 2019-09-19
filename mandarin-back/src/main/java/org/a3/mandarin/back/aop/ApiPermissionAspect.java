package org.a3.mandarin.back.aop;

import org.a3.mandarin.back.exception.ApiNotFoundException;
import org.a3.mandarin.back.exception.ApiUnauthorizedException;
import org.a3.mandarin.common.annotation.Permission;
import org.a3.mandarin.common.aop.AbstractPermissonAspect;
import org.a3.mandarin.common.aop.dao.repository.UserRepository;
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
public class ApiPermissionAspect extends AbstractPermissonAspect {
    private Logger logger= LoggerFactory.getLogger(ApiPermissionAspect.class);

    @Resource
    private UserRepository userRepository;

    @Pointcut("execution(public * org.a3.mandarin.back.controller..*.*(..))")
    public void apiPonitcut(){}

    @Around("authorize(permission) && apiPonitcut()")
    public Object doAround(ProceedingJoinPoint joinPoint, Permission permission) throws Throwable{
        ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == attributes)
            throw new ApiUnauthorizedException();

        HttpServletRequest request=attributes.getRequest();
        List<PermissionType> permissionTypes= Arrays.asList(permission.value());

        if (null == request)
            throw new ApiUnauthorizedException("impossible error");

        logger.info("CHECK PERMISSION: "+request.getRequestURL().toString());
        HttpSession session=request.getSession();
        if (null == session.getAttribute("userId"))
            throw new ApiUnauthorizedException("please login");

        Integer userId=(Integer)session.getAttribute("userId");
        User user=userRepository.findById(userId).orElse(null);

        if (null == user)
            throw new ApiNotFoundException("no such user, please login again");

        logger.info("ROLE TYPE: "+ user.getRoles().toString());

        if (!checkPermission(permissionTypes, user))
            throw new ApiUnauthorizedException("permission error");

        logger.info("PERMISSION PASSED: "+request.getRequestURL().toString());
        return joinPoint.proceed();
    }

}
