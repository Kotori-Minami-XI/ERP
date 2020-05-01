package com.Kotori.web;

import com.Kotori.domain.AjaxResult;
import com.Kotori.domain.PageListResult;
import com.Kotori.domain.QueryViewObject;
import com.Kotori.domain.Role;
import com.Kotori.mapper.RoleMapper;
import com.Kotori.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.management.relation.RoleStatus;

@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;

    @RequestMapping("/role.action")
    public String role() {
        return "/WEB-INF/view/role.jsp";
    }

    @RequestMapping("/getRoleList.action")
    @ResponseBody
    public PageListResult getRoleList(QueryViewObject queryViewObject){
        PageListResult page = roleService.getRoleList(queryViewObject);
        return page;
    }

    @RequestMapping("/saveRole.action")
    @ResponseBody
    public AjaxResult saveRole(Role role) {
        AjaxResult ajaxResult = new AjaxResult();
        try{
            roleService.saveRole(role);
            ajaxResult.setResult(true);
            ajaxResult.setMsg("添加职能成功");
        } catch (Exception e){
            e.printStackTrace();
            ajaxResult.setResult(true);
            ajaxResult.setMsg("添加职能失败");
        }
        return ajaxResult;
    }


}
