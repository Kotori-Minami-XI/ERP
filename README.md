# ERP
A one-stop ERP that constructed by SSM 

##Databse
### 1. employee table
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

### 2. department table
```
CREATE TABLE `department` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
```

### 3. menu table
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

### 4. role table
```
CREATE TABLE `role` (
  `rid` bigint NOT NULL AUTO_INCREMENT,
  `rnum` varchar(50) DEFAULT NULL,
  `rname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
```

### 5. system Log table
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

### 6. permission table
```
CREATE TABLE `permission` (
  `pid` bigint NOT NULL AUTO_INCREMENT,
  `pname` varchar(50) DEFAULT NULL,
  `presource` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
```

### 7. employee_role_rel table
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

### 8. role_permission_rel table
```
CREATE TABLE `role_permission_rel` (
  `rid` bigint NOT NULL,
  `pid` bigint NOT NULL,
  PRIMARY KEY (`rid`,`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```




