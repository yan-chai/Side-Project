package com.example.sideproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    @RequestMapping("/401")
    public String return401() {
        return "error/error401";
    }

    @RequestMapping("/noexist")
    public String noExist() {
        return "error/noexist";
    }
}
