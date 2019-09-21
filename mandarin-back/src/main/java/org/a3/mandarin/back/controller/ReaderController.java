package org.a3.mandarin.back.controller;

import org.a3.mandarin.back.exception.ApiNotFoundException;
import org.a3.mandarin.back.exception.ApiUnauthorizedException;
import org.a3.mandarin.back.model.RESTfulResponse;
import org.a3.mandarin.common.annotation.Permission;
import org.a3.mandarin.common.dao.repository.BorrowingFineHistoryRepository;
import org.a3.mandarin.common.dao.repository.UserRepository;
import org.a3.mandarin.common.entity.*;
import org.a3.mandarin.common.enums.PermissionType;
import org.a3.mandarin.common.exception.PayException;
import org.a3.mandarin.common.util.PayUtil;
import org.a3.mandarin.common.util.RoleUtil;
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
import java.util.Set;

@Controller
@RequestMapping("/api")
public class ReaderController {
    private Logger logger= LoggerFactory.getLogger(ReaderController.class);

    @Resource
    private UserRepository userRepository;

    @Resource
    private BorrowingFineHistoryRepository borrowingFineHistoryRepository;

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
        user.getRoles().add(RoleUtil.readerRole);
        userRepository.save(user);

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
        User operatorUser=userRepository.findById(operatorUserId).orElse(null);
        User targetUser=userRepository.findById(targetUserId).orElse(null);

        validateOperatorPermission(targetUser, operatorUser);
        validateUserInformation(password, phoneNumber, email, name, targetUser.getUserId());

        if (null!=email) targetUser.setEmail(email);
        if (null!=name) targetUser.setName(name);
        if (null!=phoneNumber) targetUser.setPhoneNumber(phoneNumber);
        if (null!=password) targetUser.changePassword(password);

        userRepository.save(targetUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(RESTfulResponse.ok());
    }

    @GetMapping("/reader/{id}/history/reserving")
    @ResponseBody
    @Transactional
    @Permission({PermissionType.READER, PermissionType.LIBRARIAN})
    public ResponseEntity<RESTfulResponse<List<ReservingHistory>>>  getReaderReservingHistories(@PathVariable("id") Integer targetUserId,
                                                                                                HttpSession session){
        Integer operatorUserId=(Integer) session.getAttribute("userId");
        User operatorUser=userRepository.findById(operatorUserId).orElse(null);
        User targetUser=userRepository.findById(targetUserId).orElse(null);

        validateOperatorPermission(targetUser, operatorUser);

        RESTfulResponse<List<ReservingHistory>> response=RESTfulResponse.ok();
        response.setData(targetUser.getReservingHistories());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/reader/{id}/history/borrowing")
    @ResponseBody
    @Transactional
    @Permission({PermissionType.READER, PermissionType.LIBRARIAN})
    public ResponseEntity<RESTfulResponse<List<BorrowingHistory>>> getReaderBorrowingHistories(@PathVariable("id") Integer targetUserId,
                                                                                               HttpSession session){
        Integer operatorUserId=(Integer) session.getAttribute("userId");
        User operatorUser=userRepository.findById(operatorUserId).orElse(null);
        User targetUser=userRepository.findById(targetUserId).orElse(null);

        validateOperatorPermission(targetUser, operatorUser);

        RESTfulResponse<List<BorrowingHistory>> response=RESTfulResponse.ok();
        response.setData(targetUser.getBorrowingHistories());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/reader/{id}/history/fine")
    @ResponseBody
    @Transactional
    @Permission({PermissionType.READER, PermissionType.LIBRARIAN})
    public ResponseEntity<RESTfulResponse<List<BorrowingFineHistory>>> getReaderBorrowingFineHistories(@PathVariable("id") Integer targetUserId,
                                                                                                       HttpSession session){
        Integer operatorUserId=(Integer) session.getAttribute("userId");
        User operatorUser=userRepository.findById(operatorUserId).orElse(null);
        User targetUser=userRepository.findById(targetUserId).orElse(null);

        validateOperatorPermission(targetUser, operatorUser);

        RESTfulResponse<List<BorrowingFineHistory>> response=RESTfulResponse.ok();
        response.setData(borrowingFineHistoryRepository.findBorrowingFineHistoriesByUserId(targetUserId));

        return ResponseEntity.ok(response);
    }

    private void validateOperatorPermission(User targetUser, User operatorUser){
        // only librarian and reader(self) can pass validation
        if (null == targetUser)
            throw new ApiNotFoundException("no such user");

        Set<Role> roles=operatorUser.getRoles();

        if (!roles.contains(RoleUtil.librarianRole) &&
                !(roles.contains(RoleUtil.readerRole) && operatorUser.getUserId().equals(targetUser.getUserId())))
            throw new ApiUnauthorizedException("role validation not passed");
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

        User tmpUser;
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
