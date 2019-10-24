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

    @PostMapping("/user/{name}/password/retrieve")
    @ResponseBody
    @Transactional
    // @Permission({PermissionType.ADMIN, PermissionType.LIBRARIAN, PermissionType.READER})
    public ResponseEntity<RESTfulResponse<SimpleMailMessage>> forgetPassword(
            @PathVariable("name") String nameOrPhoneNumber
    ) throws ApiNotFoundException {
        User user = userRepository.findByName(nameOrPhoneNumber);

        if (null == user)
            user = userRepository.findByPhoneNumber(nameOrPhoneNumber);

        if(null == user)
            throw new ApiNotFoundException("no such user");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(user.getEmail());
        message.setSubject("Mandarin Library: retrieve your password");
        message.setText("Here is your password. \n" +
                user.getPasswordHash() +
                "\nPlease keep it well in case you forget it. \n\n" +
                "Note, this is not a spam mail.");
        mailSender.send(message);

        RESTfulResponse<SimpleMailMessage> response = RESTfulResponse.ok();
        response.setData(message);

        return ResponseEntity.ok(response);

    }

}
