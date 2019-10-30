package org.a3.mandarin.front.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class ErrorHandleController implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

        switch (statusCode){
            case 404:
                return "reader/404";
            case 403:
                return "reader/403";
        }
        return "reader/404";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
