package org.a3.mandarin.front.controller;

import org.a3.mandarin.common.annotation.Permission;
import org.a3.mandarin.common.enums.PermissionType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/librarian")
public class LibrarianManagementController {

    @GetMapping({"", "/", "/login"})
    public String login(){
        return "librarian/login";
    }

    @GetMapping("{path}")
    @Permission(PermissionType.LIBRARIAN)
    public String map(@PathVariable("path") String path){
        return "librarian/"+path;
    }
}
