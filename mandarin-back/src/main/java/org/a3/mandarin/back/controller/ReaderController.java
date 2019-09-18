package org.a3.mandarin.back.controller;

import org.a3.mandarin.back.exception.ApiNotFoundException;
import org.a3.mandarin.back.model.RESTfulResponse;
import org.a3.mandarin.common.annotation.Permission;
import org.a3.mandarin.common.aop.dao.repository.UserRepository;
import org.a3.mandarin.common.entity.User;
import org.a3.mandarin.common.util.UserUtil;
import org.a3.mandarin.common.enums.PermissionType;
import org.a3.mandarin.common.enums.RoleType;
import org.a3.mandarin.common.exception.PayException;
import org.a3.mandarin.common.util.PayUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.Instant;

@Controller
@RequestMapping("/api")
public class ReaderController {
    @Resource
    private UserRepository userRepository;

    @PostMapping("/reader")
    @ResponseBody
    @Permission({PermissionType.LIBRARIAN})
    @Transactional
    public ResponseEntity<RESTfulResponse> registerReader(@RequestParam String name,
                                                          @RequestParam String password,
                                                          @RequestParam String phoneNumber,
                                                          @RequestParam String email,
                                                          HttpSession session,
                                                          HttpServletResponse response) throws ApiNotFoundException {
        // TODO: available checking
        if (password.length()<4)
            throw new ApiNotFoundException("password is too weak");

        // TODO: available checking
        if (phoneNumber.length()<4)
            throw new ApiNotFoundException("phone number is not available");

        // TODO: available checking
        if (email.length()<4)
            throw new ApiNotFoundException("email is not available");

        if (null != userRepository.findByName(name))
            throw new ApiNotFoundException("user name exists");

        if (null != userRepository.findByPhoneNumber(phoneNumber))
            throw new ApiNotFoundException("phone number exists");

        if (null != userRepository.findByEmail(email))
            throw new ApiNotFoundException("email exists");

        try {
            PayUtil.pay(300, "TOKEN");
        }catch (PayException e){
            throw new ApiNotFoundException("failed to pay deposit");
        }

        User user=new User(name, phoneNumber, email, Instant.now(), password);
        userRepository.save(user);
        UserUtil.setRoleByUserId(user.getUserId(), RoleType.READER);

        session.setAttribute("userId", user.getUserId());
        response.addCookie(new Cookie("userId", user.getUserId().toString()));
        return ResponseEntity.status(HttpStatus.CREATED).body(RESTfulResponse.ok());
    }
}
