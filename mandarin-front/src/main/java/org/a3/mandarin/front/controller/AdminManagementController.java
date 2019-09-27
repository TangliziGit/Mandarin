package org.a3.mandarin.front.controller;

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

    // TODO: permission
    @GetMapping({"/index", "/welcome"})
    public String index(){
        return "admin/index";
    }

    @GetMapping({"/search"})
    public String search(){
        return "admin/search";
    }

    @GetMapping({"/register"})
    public String registerLibrarian(){
        return "admin/register";
    }

    @GetMapping({"/setting"})
    public String setting(){
        return "admin/setting";
    }

    @GetMapping({"/about"})
    public String about(){
        return "admin/about";
    }
}
