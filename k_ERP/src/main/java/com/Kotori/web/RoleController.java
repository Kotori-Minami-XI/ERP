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
import java.util.List;

@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;

    /***
     * @brief  Head to role.jsp
     * @params null
     * @return Location of role.jsp
     */
    @RequestMapping("/role.action")
    public String role() {
        return "/WEB-INF/view/role.jsp";
    }

    /***
     * @breif  Obtain all roles from database
     * @params queryViewObject
     * @return role list wrapped in page
     */
    @RequestMapping("/getRolePage.action")
    @ResponseBody
    public PageListResult getRolePage(QueryViewObject queryViewObject){
        PageListResult page = roleService.getRolePage(queryViewObject);
        return page;
    }

    /***
     * @breif  Obtain all rols from database
     * @params null
     * @return List of roles
     */
    @RequestMapping("/getRoleList.action")
    @ResponseBody
    public List<Role> getRoleList(){
        List<Role> roleList = roleService.getRoleList();
        return roleList;
    }

    /***
     * @breif  Insert a new role to database, updating relation between
     *         role and permission
     * @params role
     * @return Ajax callback info indicating success or failure
     */
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

    /***
     * @brief  Update role info and relationships
     * @params role
     * @return Ajax callback info indicating success or failure
     */
    @RequestMapping("/updateRole.action")
    @ResponseBody
    public AjaxResult updateRole(Role role) {
        AjaxResult ajaxResult = new AjaxResult();
        try{
            roleService.updateRole(role);
            ajaxResult.setResult(true);
            ajaxResult.setMsg("更新职能成功");
        } catch (Exception e){
            e.printStackTrace();
            ajaxResult.setResult(true);
            ajaxResult.setMsg("更新职能失败");
        }
        return ajaxResult;
    }

    /***
     * @brief  Remove role from database by id
     * @params rid
     * @return Ajax callback info indicating success or failure
     */
    @RequestMapping("/deleteRole.action")
    @ResponseBody
    public AjaxResult deleteRole(Long rid) {
        AjaxResult ajaxResult = new AjaxResult();
        try{
            roleService.deleteRoleByRid(rid);
            ajaxResult.setResult(true);
            ajaxResult.setMsg("删除职能成功");
        } catch (Exception e){
            e.printStackTrace();
            ajaxResult.setResult(true);
            ajaxResult.setMsg("删除职能失败");
        }
        return ajaxResult;
    }

    /***
     * @brief  Get role id by employee id throw combination query
     * @params id
     * @return List of Role id belong to the employee
     */
    @RequestMapping("/getRidByEid.action")
    @ResponseBody
    public List<Long> getRidByEid(Long id) {
        return roleService.getRidByEid(id);
    }

}
