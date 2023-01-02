package com.project.evertimeclonecodingbackend.domain.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("admin")
    public String admin() {
        return "admin";
    }
}
