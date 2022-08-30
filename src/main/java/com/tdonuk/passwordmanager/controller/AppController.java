package com.tdonuk.passwordmanager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class AppController {
    @GetMapping("test")
    public ResponseEntity<?> test(HttpServletRequest request) {
        return ResponseEntity.ok("You have reached me. Your IP: " + request.getRemoteAddr());
    }

}
