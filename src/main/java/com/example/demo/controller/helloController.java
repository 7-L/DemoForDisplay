package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RequestMapping("/")
//@RestController
@Controller
public class helloController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/test2")
    public String test2() {
        return "test2";
    }
    @RequestMapping("/test3")
    public String test3() {
        return "test3";
    }
}