package org.a3.mandarin.front.controller;

import org.a3.mandarin.common.annotation.Permission;
import org.a3.mandarin.common.enums.PermissionType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ReaderFrontController {

    @GetMapping("{path}")
    public String map(@PathVariable("path") String path){
        return "reader/"+path;
    }

    @GetMapping("/account")
    @Permission(PermissionType.READER)
    public String account(){
        return "reader/account";
    }
}
