package org.a3.mandarin.back.controller;

import org.a3.mandarin.back.exception.ApiNotFoundException;
import org.a3.mandarin.back.model.RESTfulResponse;
import org.a3.mandarin.common.dao.repository.UserRepository;
import org.a3.mandarin.common.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/api")
public class UserCommonController {
    @Resource
    private UserRepository userRepository;

    @PostMapping("/user/token")
    @ResponseBody
    @Transactional
    public ResponseEntity<RESTfulResponse> login(@RequestParam String name,
                                                 @RequestParam String password,
                                                 HttpSession session,
                                                 HttpServletResponse response) throws ApiNotFoundException {
        User user=userRepository.findByName(name);

        if (null == user)
            throw new ApiNotFoundException("no such user");

        if (!user.verifyPassword(password))
            throw new ApiNotFoundException("password incorrect");

        session.setAttribute("userId", user.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(RESTfulResponse.ok());
    }

    @DeleteMapping("/user/token")
    @ResponseBody
    public ResponseEntity<RESTfulResponse> logout(HttpSession session){
        session.removeAttribute("userId");

        return ResponseEntity.ok(RESTfulResponse.ok());
    }
}
