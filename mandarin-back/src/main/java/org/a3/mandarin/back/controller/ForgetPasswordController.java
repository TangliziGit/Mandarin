package org.a3.mandarin.back.controller;

import org.a3.mandarin.back.exception.ApiNotFoundException;
import org.a3.mandarin.back.exception.ApiUnauthorizedException;
import org.a3.mandarin.back.model.RESTfulResponse;
import org.a3.mandarin.common.annotation.Permission;
import org.a3.mandarin.common.dao.repository.UserRepository;
import org.a3.mandarin.common.entity.*;
import org.a3.mandarin.common.enums.PermissionType;
import org.a3.mandarin.common.util.RoleUtil;
import org.a3.mandarin.common.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/api")
public class ForgetPasswordController {

    @Resource
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @PostMapping("/forgetpassword")
    @ResponseBody
    @Transactional
    @Permission({PermissionType.ADMIN, PermissionType.LIBRARIAN, PermissionType.READER})
    public ResponseEntity<RESTfulResponse<SimpleMailMessage>> forgetPassword(@RequestParam String name,
                                                                             @RequestParam String email) throws ApiNotFoundException {
        if (null == name || null == email)
            throw new ApiNotFoundException("please enter the information.");

        User forgetPasswordUser = userRepository.findByEmail(email);

        if(null == forgetPasswordUser)
            throw new ApiNotFoundException("user doesn't exist.");

        if(!forgetPasswordUser.getName().equals(name))
            throw new ApiNotFoundException("the email doesn't match the user.");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(forgetPasswordUser.getEmail());
        message.setSubject("retrieve password");
        message.setText("password is " + forgetPasswordUser.getPasswordHash());
        mailSender.send(message);

        RESTfulResponse<SimpleMailMessage> response = RESTfulResponse.ok();
        response.setData(message);

        return ResponseEntity.ok(response);

    }

}
