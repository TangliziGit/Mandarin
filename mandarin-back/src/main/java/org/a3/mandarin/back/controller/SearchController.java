package org.a3.mandarin.back.controller;

import org.a3.mandarin.back.exception.ApiNotFoundException;
import org.a3.mandarin.back.model.RESTfulResponse;
import org.a3.mandarin.common.annotation.Permission;
import org.a3.mandarin.common.dao.query.UserQuery;
import org.a3.mandarin.common.dao.repository.UserRepository;
import org.a3.mandarin.common.entity.User;
import org.a3.mandarin.common.enums.PermissionType;
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
import java.time.Instant;
import java.util.List;

@Controller
@RequestMapping("/api")
public class SearchController {

    @Resource
    private UserRepository userRepository;

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
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "signUpTimeAfter", required = false) Instant signUpTimeAfter,
            @RequestParam(value = "signUpTimeBefore", required = false) Instant signUpTimeBefore){
        // TODO: TEST NOT PASSED!!!!
        List<User> users;

        UserQuery userQuery=new UserQuery(){{
            setCombineLogicType(LogicType.AND);
            setUserIdEqual(userId);
            setNameLike(name);
            setPhoneNumberLike(phoneNumber);
            setEmailLike(email);
            setSignUpTimeAfter(signUpTimeAfter);
            setSignUpTimeBefore(signUpTimeBefore);
        }};

        users=userRepository.findReadersWithSpec(
                userQuery.toSpec(),
                PageRequest.of(page, limit, Sort.by("sign_up_time"))
        );

        if (null == users)
            throw new ApiNotFoundException("no such user");

        RESTfulResponse<List<User>> response=RESTfulResponse.ok();
        response.setData(users);
        return ResponseEntity.ok(response);
    }
}
