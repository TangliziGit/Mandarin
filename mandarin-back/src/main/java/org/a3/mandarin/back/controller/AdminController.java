package org.a3.mandarin.back.controller;

import org.a3.mandarin.back.exception.ApiNotFoundException;
import org.a3.mandarin.back.exception.ApiUnauthorizedException;
import org.a3.mandarin.back.model.RESTfulResponse;
import org.a3.mandarin.common.annotation.Permission;
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
public class AdminController {
    private Logger logger= LoggerFactory.getLogger(AdminController.class);

    @Resource
    private UserRepository userRepository;

}
