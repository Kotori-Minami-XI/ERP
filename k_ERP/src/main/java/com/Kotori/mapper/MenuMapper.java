package com.Kotori.mapper;

import com.Kotori.domain.Menu;
import java.util.List;

public interface MenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Menu record);

    Menu selectByPrimaryKey(Long id);

    List<Menu> selectAll();

    int updateByPrimaryKey(Menu record);

    Long getParentIdById(Long id);

    void deleteParentMenuRelById(Long id);

    List<Menu> getMenuTree();

    void listChildMenu(Long id);
}