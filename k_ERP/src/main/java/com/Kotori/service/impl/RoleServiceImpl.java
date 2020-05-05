package com.Kotori.service.impl;

import com.Kotori.domain.*;
import com.Kotori.mapper.RoleMapper;
import com.Kotori.service.RoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageListResult getRolePage(QueryViewObject queryViewObject) {
        Page<Object> page = PageHelper.startPage(queryViewObject.getPage(), queryViewObject.getRows());
        List<Role> roleList = roleMapper.selectAll();

        PageListResult pageListResult = new PageListResult();
        pageListResult.setTotal(page.getTotal());
        pageListResult.setRows(roleList);
        return pageListResult;
    }

    @Override
    public void saveRole(Role role) {
        // Step 1: Save role and obtain rid from insertion operation
        roleMapper.insert(role);

        // Step 2: insert relation between role and permission
        for (Permission permission : role.getPermissions()) {
            roleMapper.insertRoleAndPermissionRel(role.getRid(), permission.getPid());
        }
    }

    @Override
    public void updateRole(Role role) {
        // Step 1: Remove relation between role and permission
        roleMapper.deletePermissionAndRoleRelByRid(role.getRid());

        // Step 2: Update role table by rid
        roleMapper.updateByPrimaryKey(role);

        // Step 3: Reestablish relation between role and permission
        for (Permission permission : role.getPermissions()) {
            roleMapper.insertRoleAndPermissionRel(role.getRid(), permission.getPid());
        }
    }

    @Override
    public void deleteRoleByRid(Long rid) {
        roleMapper.deletePermissionAndRoleRelByRid(rid);
        roleMapper.deleteByPrimaryKey(rid);
    }

    @Override
    public List<Role> getRoleList() {
        return roleMapper.selectAll();
    }

    @Override
    public List<Long> getRidByEid(Long id) {
        return roleMapper.getRidByEid(id);
    }
}
