package com.nixime.nixauth.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/admin")
    public String home(Principal principal) {
        return "Hello, " + principal.getName();
    }

}
