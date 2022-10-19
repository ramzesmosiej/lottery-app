package com.ramzescode.socials.security;

public enum ApplicationUserPermissions {
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    POST_WRITE("post:write"),
    POST_READ("post:read");

    private final String permission;

    ApplicationUserPermissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
