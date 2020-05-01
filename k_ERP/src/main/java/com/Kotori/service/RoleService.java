package com.Kotori.service;

import com.Kotori.domain.PageListResult;
import com.Kotori.domain.QueryViewObject;
import com.Kotori.domain.Role;

import java.util.List;

public interface RoleService {
    PageListResult getRolePage(QueryViewObject queryViewObject);

    void saveRole(Role role);

    void updateRole(Role role);

    void deleteRoleByRid(Long rid);

    List<Role> getRoleList();

    List<Long> getRidByEid(Long eid);
}
