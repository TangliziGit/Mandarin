package org.a3.mandarin.back.controller;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.a3.mandarin.back.exception.ApiForbiddenException;
import org.a3.mandarin.back.model.RESTfulResponse;
import org.a3.mandarin.common.annotation.Permission;
import org.a3.mandarin.common.dao.repository.UserQueryRepository;
import org.a3.mandarin.common.dao.repository.UserRepository;
import org.a3.mandarin.common.entity.QUser;
import org.a3.mandarin.common.entity.User;
import org.a3.mandarin.common.enums.PermissionType;
import org.a3.mandarin.common.util.RoleUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/api")
public class SearchController {

    @Resource
    private UserQueryRepository userQueryRepository;

    @PostMapping("/search/reader")
    @ResponseBody
    @Transactional
    @Permission(PermissionType.LIBRARIAN)
    public ResponseEntity<RESTfulResponse<List<User>>> searchReader(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit,
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "email", required = false) String email){
        if (null == userId && null == name && null == phoneNumber && null == email)
            throw new ApiForbiddenException("please enter any parameters");

        QUser qUser=QUser.user;
        PageRequest pageRequest=PageRequest.of(page, limit, Sort.by("signUpTime"));
        BooleanExpression expression=qUser.roles.contains(RoleUtil.readerRole);

        if (null != name) expression=expression.and(qUser.name.contains(name));
        if (null != email) expression=expression.and(qUser.email.eq(email));
        if (null != userId) expression=expression.and(qUser.userId.eq(userId));
        if (null != phoneNumber) expression=expression.and(qUser.phoneNumber.eq(phoneNumber));

        Page<User> userPage=userQueryRepository.findAll(expression, pageRequest);

        RESTfulResponse<List<User>> response=RESTfulResponse.ok();
        response.setData(userPage.getContent());
        return ResponseEntity.ok(response);
    }
}
