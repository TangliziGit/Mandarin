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
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/api")
public class UserCommonController {
    @Resource
    private UserRepository userRepository;

    @PostMapping("/user/token")
    @ResponseBody
    @Transactional
    public ResponseEntity<RESTfulResponse<User>> login(@RequestParam String name,
                                                       @RequestParam String password,
                                                       HttpSession session) throws ApiNotFoundException {
        User user=userRepository.findByName(name);

        if (null == user)
            user=userRepository.findByPhoneNumber(name);

        if (null == user)
            throw new ApiNotFoundException("no such user");

        if (!user.verifyPassword(password))
            throw new ApiNotFoundException("password incorrect");

        RESTfulResponse<User> response=RESTfulResponse.ok();
        response.setData(user);

        session.setAttribute("userId", user.getUserId());
        session.setAttribute("userName", user.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/user/token")
    @ResponseBody
    public ResponseEntity<RESTfulResponse> logout(HttpSession session){
        session.removeAttribute("userId");
        session.removeAttribute("userName");

        return ResponseEntity.ok(RESTfulResponse.ok());
    }

    @GetMapping("/user/current")
    @ResponseBody
    @Transactional
    public ResponseEntity<RESTfulResponse<User>> getCurrentUser(HttpSession session){
        Integer userId=(Integer) session.getAttribute("userId");

        if (null == userId)
            throw new ApiNotFoundException("please login");

        User user=userRepository.findById(userId).orElse(null);

        RESTfulResponse<User> response=RESTfulResponse.ok();
        response.setData(user);
        return ResponseEntity.ok(response);
    }
}
