package com.Kotori.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RoleController {
    @RequestMapping("role.action")
    public String role() {
        return "/WEB-INF/view/role.jsp";
    }
}
