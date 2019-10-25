package org.a3.mandarin.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReaderFrontHelperController {

    @GetMapping({"", "/"})
    public String index(){
        return "reader/index";
    }
}
