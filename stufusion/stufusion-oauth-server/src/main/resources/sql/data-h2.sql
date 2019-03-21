--Users
INSERT INTO `users`(`ID`,`PASSWORD`,`ENABLED`,`FIRST_NAME`,`LAST_NAME`, `EMAIL`,`USERID`,`USERTYPE`) values (201,'$2a$10$Ch/S4T8gFsZPtdJPZ53V7e11rgNNG5EG8HCq8k7frXjJLW0NAiUEi',1,'acme','runner','acme@stufusion.com','stu123','NA');
INSERT INTO `users`(`ID`,`PASSWORD`,`ENABLED`,`FIRST_NAME`,`LAST_NAME`, `EMAIL`,`USERID`,`USERTYPE`) values (202,'$2a$10$Ch/S4T8gFsZPtdJPZ53V7e11rgNNG5EG8HCq8k7frXjJLW0NAiUEi',1,'John','Smith','admin@stufusion.com','johnSm123','NA');
INSERT INTO `users`(`ID`,`PASSWORD`,`ENABLED`,`FIRST_NAME`,`LAST_NAME`, `EMAIL`,`USERID`,`USERTYPE`) values (203,'$2a$10$Ch/S4T8gFsZPtdJPZ53V7e11rgNNG5EG8HCq8k7frXjJLW0NAiUEi',1,'Emma','Watson','emma@stufusion.com','Emma991','NA');


--Roles
INSERT INTO `roles`(`ID`,`ROLENAME`) values (1,'ROLE_ADMIN'),(2,'ROLE_STUDENT'),(3,'ROLE_FACULTY'),(4,'ROLE_EMPLOYER'),(5,'ROLE_ALUMNI'),(6,'ROLE_USER');


--permission
INSERT INTO `permissions`(`ID`,`PERMISSIONNAME`) values (100,'CTRL_FEED_GET');
INSERT INTO `permissions`(`ID`,`PERMISSIONNAME`) values (101,'CTRL_FEED_CREATE');
INSERT INTO `permissions`(`ID`,`PERMISSIONNAME`) values (102,'CTRL_FEED_DELETE');
INSERT INTO `permissions`(`ID`,`PERMISSIONNAME`) values (103,'CTRL_ALL_ACCESS');


--permission-roles
INSERT INTO `role_permissions`(`ROLE_ID`,`PERMISSION_ID`) values (2,100);
INSERT INTO `role_permissions`(`ROLE_ID`,`PERMISSION_ID`) values (2,101);
INSERT INTO `role_permissions`(`ROLE_ID`,`PERMISSION_ID`) values (2,102);


--user-roles-admin(should have all the roles and permssions)
INSERT INTO `role_permissions`(`ROLE_ID`,`PERMISSION_ID`) values (1,103);
INSERT INTO `role_permissions`(`ROLE_ID`,`PERMISSION_ID`) values (1,100);
INSERT INTO `role_permissions`(`ROLE_ID`,`PERMISSION_ID`) values (1,101);
INSERT INTO `role_permissions`(`ROLE_ID`,`PERMISSION_ID`) values (1,102);

-- users-roles
INSERT INTO `user_roles`(`USER_ID`,`ROLE_ID`) values (201,2);
INSERT INTO `user_roles`(`USER_ID`,`ROLE_ID`) values (203,3);

--user-roles-admin(should have all the roles and permssions)
INSERT INTO `user_roles`(`USER_ID`,`ROLE_ID`) values (202,1);
