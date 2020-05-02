package com.Kotori.web.realm;

import com.Kotori.domain.Employee;
import com.Kotori.domain.Role;
import com.Kotori.service.EmployeeService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;
import java.util.List;

public class EmployeeRealm extends AuthorizingRealm {

    @Autowired
    private EmployeeService employeeService;

    /***
     * @brief  Obtain authorization info when trying to login
     * @params token
     * @return AuthenticationInfo
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = token.getPrincipal().toString();
        Employee employee = employeeService.getEmployeeByUsername(username);
        if (null == employee) {
            return null;
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(employee, employee.getPassword(), this.getName());
        return info;
    }

    /***
     * @brief  Obtain and verify authorization info when visiting methods annotated
     *         with @RequiresPermissions
     * @params principals
     * @return AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // Obtain Subject info from AuthenticationInfo
        Employee employee = (Employee) principals.getPrimaryPrincipal();
        List<String> roles = new ArrayList<>();
        List<String> permissions = new ArrayList<>();

        // Grant all access if employee is admin
        if (employee.getAdmin()) {
            permissions.add("*:*");
        } else {
            roles = employeeService.getRolesById(employee.getId());
            permissions = employeeService.getPermissionsById(employee.getId());
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roles);
        simpleAuthorizationInfo.addStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }
}
