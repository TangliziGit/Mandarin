package org.a3.mandarin.back.controller;

import org.a3.mandarin.back.exception.ApiNotFoundException;
import org.a3.mandarin.back.exception.ApiUnauthorizedException;
import org.a3.mandarin.back.model.RESTfulResponse;
import org.a3.mandarin.common.annotation.Permission;
import org.a3.mandarin.common.aop.dao.repository.UserRepository;
import org.a3.mandarin.common.entity.User;
import org.a3.mandarin.common.util.UserUtil;
import org.a3.mandarin.common.enums.PermissionType;
import org.a3.mandarin.common.enums.RoleType;
import org.a3.mandarin.common.exception.PayException;
import org.a3.mandarin.common.util.PayUtil;
import org.a3.mandarin.common.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.List;

@Controller
@RequestMapping("/api")
public class ReaderController {
    private Logger logger= LoggerFactory.getLogger(ReaderController.class);

    @Resource
    private UserRepository userRepository;

    @PostMapping("/reader")
    @ResponseBody
    @Transactional
    @Permission({PermissionType.LIBRARIAN})
    public ResponseEntity<RESTfulResponse> registerReader(@RequestParam String name,
                                                          @RequestParam String password,
                                                          @RequestParam String phoneNumber,
                                                          @RequestParam String email,
                                                          HttpSession session,
                                                          HttpServletResponse response) throws ApiNotFoundException {
        validateUserInformation(password, phoneNumber, email, name, null);

        try {
            // TODO: SettingUtil
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

    @PutMapping("/reader/{id}")
    @ResponseBody
    @Transactional
    @Permission({PermissionType.READER, PermissionType.LIBRARIAN})
    public ResponseEntity<RESTfulResponse> updateReader(@PathVariable("id") Integer targetUserId,
                                                        @RequestParam(required = false) String name,
                                                        @RequestParam(required = false) String phoneNumber,
                                                        @RequestParam(required = false) String email,
                                                        @RequestParam(required = false) String password,
                                                        HttpSession session){
        // TODO: can librarian reset reader's password?
        Integer operatorUserId=(Integer) session.getAttribute("userId");
        User targetUser=userRepository.findById(targetUserId).orElse(null);

        if (null == targetUser)
            throw new ApiNotFoundException("no such user");

        List<RoleType> roles=UserUtil.getRolesByUserId(operatorUserId);
        logger.debug("OPERATOR ID: "+operatorUserId.toString());
        logger.debug("TARGET ID: "+targetUserId.toString());

        // librarian can update reader's information
        // reader can update themselves
        // admin can not use this function

        // so, only librarian and reader(self) can use it
        if (!roles.contains(RoleType.LIBRARIAN) &&
                !(roles.contains(RoleType.READER) && operatorUserId.equals(targetUserId)))
            throw new ApiUnauthorizedException("role validation not passed");

        validateUserInformation(password, phoneNumber, email, name, targetUser.getUserId());

        if (null!=email) targetUser.setEmail(email);
        if (null!=name) targetUser.setName(name);
        if (null!=phoneNumber) targetUser.setPhoneNumber(phoneNumber);
        if (null!=password) targetUser.changePassword(password);

        userRepository.save(targetUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(RESTfulResponse.ok());
    }

    private void validateUserInformation(String password, String phoneNumber, String email, String name, Integer userId) throws ApiNotFoundException{
        if (null!=name && !ValidateUtil.validateName(name))
            throw new ApiNotFoundException("name is not available");

        if (null!=password && !ValidateUtil.validatePassword(password))
            throw new ApiNotFoundException("password is too weak");

        if (null!=phoneNumber && !ValidateUtil.validatePhoneNumber(phoneNumber))
            throw new ApiNotFoundException("phone number is not available");

        if (null!=email && !ValidateUtil.validateEmail(email))
            throw new ApiNotFoundException("email is not available");

        User tmpUser=null;
        if (null!=name) {
            tmpUser = userRepository.findByName(name);
            if (null != tmpUser && !tmpUser.getUserId().equals(userId))
                throw new ApiNotFoundException("user name exists");
        }

        if (null!=phoneNumber) {
            tmpUser = userRepository.findByPhoneNumber(phoneNumber);
            if (null != tmpUser && !tmpUser.getUserId().equals(userId))
                throw new ApiNotFoundException("phone number exists");
        }

        if (null != email) {
            tmpUser = userRepository.findByEmail(email);
            if (null != tmpUser && !tmpUser.getUserId().equals(userId))
                throw new ApiNotFoundException("email exists");
        }
    }
}
