package com.Kotori.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MenuController {
    @RequestMapping("menu.action")
    public String menu() {
        return "/WEB-INF/view/menu.jsp";
    }
}
