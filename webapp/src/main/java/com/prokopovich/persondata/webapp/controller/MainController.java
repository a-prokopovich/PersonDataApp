package com.prokopovich.persondata.webapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public String index() {

        return "<h1>Welcome!</h1>";
    }
}
