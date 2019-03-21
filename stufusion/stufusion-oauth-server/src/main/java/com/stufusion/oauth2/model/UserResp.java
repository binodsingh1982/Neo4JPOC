package com.stufusion.oauth2.model;

import java.util.Map;

public class UserResp {

    private Long id;

    private String email;

    private String userType;

    private String userId;

    private boolean enabled;

    private String firstName;

    private String lastName;

    private String token;

    private String tokenExpiry;

    private Map<Long, String> roles;

    private Map<Long, String> permissions;

    private Map<String, String> authProviders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Map<Long, String> getRoles() {
        return roles;
    }

    public void setRoles(Map<Long, String> roles) {
        this.roles = roles;
    }

    public Map<Long, String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Map<Long, String> permissions) {
        this.permissions = permissions;
    }

    public Map<String, String> getAuthProviders() {
        return authProviders;
    }

    public void setAuthProviders(Map<String, String> authProviders) {
        this.authProviders = authProviders;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenExpiry() {
        return tokenExpiry;
    }

    public void setTokenExpiry(String tokenExpiry) {
        this.tokenExpiry = tokenExpiry;
    }

}
