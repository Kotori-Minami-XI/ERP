package com.Kotori.web;

import com.Kotori.domain.Permission;
import com.Kotori.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/getPermissionList.action")
    @ResponseBody
    public List<Permission> getPermissionList(){
        List<Permission> permissionList = permissionService.getPermissionList();
        return permissionList;
    }

    @RequestMapping("/getCurrentPermissionByRid.action")
    @ResponseBody
    public List<Permission> getCurrentPermissionByRid(Long rid) {
        List<Permission> permissionList = permissionService.getCurrentPermissionByRid(rid);
        return permissionList;
    }
}
