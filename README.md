# ERP
A one-stop ERP that constructed by SSM 

## Database 
### Table generation
#### 1. employee table
```
CREATE TABLE `employee` (
     `id` bigint NOT NULL AUTO_INCREMENT,
     `username` varchar(50) DEFAULT NULL,
     `inputtime` datetime DEFAULT NULL,
     `tel` varchar(20) DEFAULT NULL,
     `email` varchar(50) DEFAULT NULL,
     `state` tinyint(1) DEFAULT NULL,
     `admin` tinyint(1) DEFAULT NULL,
     `dept_no` bigint DEFAULT NULL,
     `password` varchar(50) DEFAULT NULL,
     PRIMARY KEY (`id`),
     KEY `dept_no` (`dept_no`),
     CONSTRAINT `out` FOREIGN KEY (`dept_no`) REFERENCES `department` (`id`)
   ) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
```

```aidl
INSERT INTO `k_erp`.`employee`(`id`, `username`, `inputtime`, `tel`, `email`, `state`, `admin`, `dept_no`, `password`) VALUES (1, 'Kotori', '2020-04-08 16:00:00', '18600000001', 'Kotori@qq.com', 0, 0, 1, '1234');
INSERT INTO `k_erp`.`employee`(`id`, `username`, `inputtime`, `tel`, `email`, `state`, `admin`, `dept_no`, `password`) VALUES (2, 'Honoka', '2020-04-07 16:00:00', '18600000002', 'Honoka@qq.com', 0, 0, 1, '1234');
INSERT INTO `k_erp`.`employee`(`id`, `username`, `inputtime`, `tel`, `email`, `state`, `admin`, `dept_no`, `password`) VALUES (3, 'Eri', '2020-04-08 16:00:00', '18600000003', 'Eri@qq.com', 0, 1, 1, '1234');
INSERT INTO `k_erp`.`employee`(`id`, `username`, `inputtime`, `tel`, `email`, `state`, `admin`, `dept_no`, `password`) VALUES (4, 'Nozomi', '2020-04-07 16:00:00', '18600000004', 'Nozomi@qq.com', 0, 1, 1, '1234');
INSERT INTO `k_erp`.`employee`(`id`, `username`, `inputtime`, `tel`, `email`, `state`, `admin`, `dept_no`, `password`) VALUES (5, 'Nico', '2020-05-22 07:56:05', '18600000005', 'Nico@qq.com', 0, 1, 1, '1234');
INSERT INTO `k_erp`.`employee`(`id`, `username`, `inputtime`, `tel`, `email`, `state`, `admin`, `dept_no`, `password`) VALUES (6, 'Umi', '2020-05-28 16:00:00', '18600000006', 'Umi@qq.com', 1, 1, 1, '1234');
```

#### 2. department table
```
CREATE TABLE `department` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
```

```aidl
INSERT INTO `k_erp`.`department`(`id`, `name`) VALUES (1, '技术部');
INSERT INTO `k_erp`.`department`(`id`, `name`) VALUES (2, '市场部');
```

#### 3. menu table
```
CREATE TABLE `menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `text` varchar(50) DEFAULT NULL,
  `url` varchar(50) DEFAULT NULL,
  `parent_id` bigint DEFAULT NULL,
  `permission_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`),
  KEY `permission_id` (`permission_id`),
  CONSTRAINT `menu_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `menu` (`id`),
  CONSTRAINT `menu_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
```

```aidl
INSERT INTO `k_erp`.`menu`(`id`, `text`, `url`, `parent_id`, `permission_id`) VALUES (1, '系统管理', NULL, NULL, NULL);
INSERT INTO `k_erp`.`menu`(`id`, `text`, `url`, `parent_id`, `permission_id`) VALUES (2, '员工管理', '/employee.action', 1, 4);
INSERT INTO `k_erp`.`menu`(`id`, `text`, `url`, `parent_id`, `permission_id`) VALUES (3, '职能管理', '/role.action', 1, 5);
INSERT INTO `k_erp`.`menu`(`id`, `text`, `url`, `parent_id`, `permission_id`) VALUES (4, '菜单管理', '/menu.action', 1, 6);
```

#### 4. role table
```
CREATE TABLE `role` (
  `rid` bigint NOT NULL AUTO_INCREMENT,
  `rnum` varchar(50) DEFAULT NULL,
  `rname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
```

```aidl
INSERT INTO `k_erp`.`role`(`rid`, `rnum`, `rname`) VALUES (1, 'admin', '管理员');
INSERT INTO `k_erp`.`role`(`rid`, `rnum`, `rname`) VALUES (2, 'hr', '人事');
INSERT INTO `k_erp`.`role`(`rid`, `rnum`, `rname`) VALUES (3, 'manager', '经理');
```

#### 5. system Log table
```
CREATE TABLE `systemlog` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `optime` datetime DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `function` varchar(255) DEFAULT NULL,
  `params` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=209 DEFAULT CHARSET=utf8;
```

#### 6. permission table
```
CREATE TABLE `permission` (
  `pid` bigint NOT NULL AUTO_INCREMENT,
  `pname` varchar(50) DEFAULT NULL,
  `presource` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
```

```aidl
INSERT INTO `k_erp`.`permission`(`pid`, `pname`, `presource`) VALUES (1, '员工添加', 'employee:add');
INSERT INTO `k_erp`.`permission`(`pid`, `pname`, `presource`) VALUES (2, '员工删除', 'employee:delete');
INSERT INTO `k_erp`.`permission`(`pid`, `pname`, `presource`) VALUES (3, '员工编辑', 'employee:edit');
INSERT INTO `k_erp`.`permission`(`pid`, `pname`, `presource`) VALUES (4, '员工主页', 'employee:index');
INSERT INTO `k_erp`.`permission`(`pid`, `pname`, `presource`) VALUES (5, '角色主页', 'role:index');
INSERT INTO `k_erp`.`permission`(`pid`, `pname`, `presource`) VALUES (6, '菜单主页', 'menu:index');
```

#### 7. employee_role_rel table
```
CREATE TABLE `employee_role_rel` (
  `eid` bigint NOT NULL,
  `rid` bigint NOT NULL,
  PRIMARY KEY (`eid`,`rid`),
  KEY `rid` (`rid`),
  CONSTRAINT `employee_role_rel_ibfk_1` FOREIGN KEY (`eid`) REFERENCES `employee` (`id`),
  CONSTRAINT `employee_role_rel_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `role` (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

```aidl
INSERT INTO `k_erp`.`employee_role_rel`(`eid`, `rid`) VALUES (1, 1);
INSERT INTO `k_erp`.`employee_role_rel`(`eid`, `rid`) VALUES (1, 2);
INSERT INTO `k_erp`.`employee_role_rel`(`eid`, `rid`) VALUES (1, 3);
```

#### 8. role_permission_rel table
```
CREATE TABLE `role_permission_rel` (
  `rid` bigint NOT NULL,
  `pid` bigint NOT NULL,
  PRIMARY KEY (`rid`,`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```
```aidl
INSERT INTO `k_erp`.`role_permission_rel`(`rid`, `pid`) VALUES (1, 1);
INSERT INTO `k_erp`.`role_permission_rel`(`rid`, `pid`) VALUES (1, 2);
INSERT INTO `k_erp`.`role_permission_rel`(`rid`, `pid`) VALUES (1, 3);
INSERT INTO `k_erp`.`role_permission_rel`(`rid`, `pid`) VALUES (1, 4);
INSERT INTO `k_erp`.`role_permission_rel`(`rid`, `pid`) VALUES (1, 5);
INSERT INTO `k_erp`.`role_permission_rel`(`rid`, `pid`) VALUES (1, 6);
```



