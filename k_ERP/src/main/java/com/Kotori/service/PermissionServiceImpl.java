package com.Kotori.service;

import com.Kotori.domain.Permission;
import com.Kotori.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> getPermissionList() {
        List<Permission> permissionList = permissionMapper.selectAll();
        return permissionList;
    }

    @Override
    public List<Permission> getCurrentPermissionByRid(Long rid) {
        List<Permission> permissionList = permissionMapper.getCurrentPermissionByRid(rid);
        return permissionList;
    }
}
