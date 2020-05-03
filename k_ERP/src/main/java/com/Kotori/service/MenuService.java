package com.Kotori.service;

import com.Kotori.domain.AjaxResult;
import com.Kotori.domain.Menu;
import com.Kotori.domain.PageListResult;
import com.Kotori.domain.QueryViewObject;

import java.util.List;

public interface MenuService {
    PageListResult getMenuPage(QueryViewObject queryViewObject);

    List<Menu> getParentList();

    void saveMenu(Menu menu);

    AjaxResult updateMenu(Menu menu);

    void deleteMenuById(Long id);

    List<Menu> getMenuTree();
}
