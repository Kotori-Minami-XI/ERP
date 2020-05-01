package com.Kotori.service;

import com.Kotori.domain.PageListResult;
import com.Kotori.domain.QueryViewObject;
import com.Kotori.domain.Role;

public interface RoleService {
    PageListResult getRoleList(QueryViewObject queryViewObject);

    void saveRole(Role role);
}
