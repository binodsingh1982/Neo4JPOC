package com.stufusion.oauth2.entity;

import com.stufusion.oauth2.exception.ApiSystemException;

public enum UserRoleType {
    STUDENT("Student", "ROLE_STUDENT"),
    ALUMNI("Alumni", "ROLE_ALUMNI"),
    EMPLOYER("Employer", "ROLE_EMPLOYER"),
    FACULTY("Faculty", "ROLE_FACULTY"),
    ADMIN("Admin", "ROLE_ADMIN");

    private String name;

    private String role;

    UserRoleType(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public static UserRoleType getRoleByName(String name) {
        for (UserRoleType userType : UserRoleType.values()) {
            if (userType.getName().equals(name)) {
                return userType;
            }
        }
        throw new ApiSystemException("Invalid role name : " + name);
    }

}
