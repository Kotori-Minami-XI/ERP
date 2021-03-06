package com.Kotori.web;

import com.Kotori.domain.*;
import com.Kotori.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Iterator;
import java.util.List;

@Controller
public class MenuController {
    @Autowired
    private MenuService menuService;

    /***
     * @breif  Forward to menu page
     * @params null
     * @return Resource location of menu page
     */
    @RequestMapping("menu.action")
    public String menu() {
        return "/WEB-INF/view/menu.jsp";
    }

    /***
     * @breif  Obtain menu page
     * @params queryViewObject
     * @return Obtained menu items in page
     */
    @RequestMapping("/getMenuPage.action")
    @ResponseBody
    public PageListResult getMenuPage(QueryViewObject queryViewObject) {
        PageListResult pageListResult = menuService.getMenuPage(queryViewObject);
        return pageListResult;
    }

    /***
     * @brief  Obtain all parent list items from database
     * @params null
     * @return List of parent menu
     */
    @RequestMapping("/getParentList.action")
    @ResponseBody
    public List<Menu> getParentList() {
        return menuService.getParentList();
    }

    /***
     * @brief  Receive submitted form and save menu in database
     * @params menu
     * @return AjaxResult indicating success or failure
     */
    @RequestMapping("/saveMenu.action")
    @ResponseBody
    public AjaxResult saveMenu(Menu menu) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            menuService.saveMenu(menu);
            ajaxResult.setResult(true);
            ajaxResult.setMsg("保存成功");
        } catch (Exception e) {
            ajaxResult.setResult(false);
            ajaxResult.setMsg("保存失败");
        }
        return ajaxResult;
    }

    /***
     * @brief  Update menu into database
     * @params menu
     * @return AjaxResult indicating success or failure
     */
    @RequestMapping("/updateMenu.action")
    @ResponseBody
    public AjaxResult updateMenu(Menu menu) {
        return menuService.updateMenu(menu);
    }

    /***
     * @brief  Remove menu from database by id
     * @params id
     * @return AjaxResult indicating success or failure
     */
    @RequestMapping("/deleteMenuById.action")
    @ResponseBody
    public AjaxResult deleteMenuById(Long id) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            menuService.deleteMenuById(id);
            ajaxResult.setResult(true);
            ajaxResult.setMsg("删除成功");
        } catch (Exception e) {
            ajaxResult.setResult(false);
            ajaxResult.setMsg("删除失败");
        }
        return ajaxResult;
    }

    /***
     * @brief  Obtain the current subject form security manager and
     *         check access for the subject if the subject is not admin
     * @params null
     * @return List of menu which contains available access for the
     *         current subject
     */
    @RequestMapping("/getMenuTree.action")
    @ResponseBody
    public List<Menu> getMenuTree() {
        List<Menu> menuTree = menuService.getMenuTree();

        // Get subject
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();

        // Check authorization and remove unauthorized items by regression
        if (!employee.getAdmin()) {
            checkPermission(menuTree);
        }
        return menuTree;
    }

    /****
     * @brief  Iterate all sub menu by regression and remove unavailable
     *         access for the current subject
     * @params menuTree
     * @return null
     */
    private void checkPermission(List<Menu> menuTree) {
        Subject subject = SecurityUtils.getSubject();
        // Remove menus that are not authorized for the current subject
        Iterator<Menu> iterator = menuTree.iterator();

        while (iterator.hasNext()) {
            Menu menu = iterator.next();
            if (null != menu.getPermission()) {
                String presource = menu.getPermission().getPresouce();
                if (!subject.isPermitted(presource)) {
                    iterator.remove();
                    continue;
                }
            }
            // Regression to check all child menus
            if (menu.getChildren().size() > 0) {
                checkPermission(menu.getChildren());
            }
        }
    }


}
