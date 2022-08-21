package com.tdonuk.passwordmanager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class BaseController {
    @GetMapping("test")
    public ResponseEntity<?> test(HttpServletRequest request) {
        return ResponseEntity.ok("You have reached me. Your IP: " + request.getRemoteHost());
    }
}
