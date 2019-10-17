package org.a3.mandarin.front.controller;

import org.a3.mandarin.common.annotation.Permission;
import org.a3.mandarin.common.enums.PermissionType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminManagementController {

    @GetMapping({"", "/", "/login"})
    public String login(){
        return "admin/login";
    }

    @GetMapping({"/index", "/welcome"})
    @Permission(PermissionType.ADMIN)
    public String index(){
        return "admin/welcome";
    }

    @GetMapping({"/search"})
    @Permission(PermissionType.ADMIN)
    public String search(){
        return "admin/search";
    }

    @GetMapping({"/register"})
    @Permission(PermissionType.ADMIN)
    public String registerLibrarian(){
        return "admin/register";
    }

    @GetMapping({"/setting"})
    @Permission(PermissionType.ADMIN)
    public String setting(){
        return "admin/setting";
    }

    @GetMapping({"/about"})
    @Permission(PermissionType.ADMIN)
    public String about(){
        return "admin/about";
    }
}
