package com.Kotori.service.impl;

import com.Kotori.domain.AjaxResult;
import com.Kotori.domain.Menu;
import com.Kotori.domain.PageListResult;
import com.Kotori.domain.QueryViewObject;
import com.Kotori.mapper.MenuMapper;
import com.Kotori.service.MenuService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public PageListResult getMenuPage(QueryViewObject queryViewObject) {
        // Compute items range (Concat limit(X, X) into sql statement)
        Page<Menu> page = PageHelper.startPage(queryViewObject.getPage(), queryViewObject.getRows());
        List<Menu> menuList = menuMapper.selectAll();

        // Set current page and total page displayed in jsp
        PageListResult pageListResult = new PageListResult();
        pageListResult.setRows(menuList);
        pageListResult.setTotal(page.getTotal());
        return pageListResult;
    }

    @Override
    public List<Menu> getParentList() {
        return menuMapper.selectAll();
    }

    @Override
    public void saveMenu(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public AjaxResult updateMenu(Menu menu) {
        AjaxResult ajaxResult = new AjaxResult();

        // Do not chose son menu as father menu
        Long parentId = menuMapper.getParentIdById(menu.getParent().getId());
        if (parentId == menu.getId()) {
            ajaxResult.setResult(false);
            ajaxResult.setMsg("更新失败, 请不要将子菜单设置成自己的父菜单");
            return ajaxResult;
        }

        try {
            menuMapper.updateByPrimaryKey(menu);
            ajaxResult.setResult(true);
            ajaxResult.setMsg("更新成功");
        } catch (Exception e) {
            ajaxResult.setResult(false);
            ajaxResult.setMsg("更新失败");
        }
        return ajaxResult;
    }

    @Override
    public void deleteMenuById(Long id) {
        // Break relation between parent menu and sub menu
        menuMapper.deleteParentMenuRelById(id);

        menuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Menu> getMenuTree() {
        return menuMapper.getMenuTree();
    }
}
