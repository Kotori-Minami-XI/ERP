package com.Kotori.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login.action")
    public String login() {
        return "redirect:/login.jsp";
    }
}
