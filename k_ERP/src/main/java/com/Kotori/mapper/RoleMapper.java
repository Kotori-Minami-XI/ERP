package com.Kotori.mapper;

import com.Kotori.domain.PageListResult;
import com.Kotori.domain.QueryViewObject;
import com.Kotori.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long rid);

    int insert(Role record);

    Role selectByPrimaryKey(Long rid);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    void insertRoleAndPermissionRel(@Param("rid") Long rid, @Param("pid")Long pid);

    void deletePermissionAndRoleRelByRid(Long rid);
}