package com.example.manajemenstokbarang;

import android.app.Application;

public class UserRoles extends Application {
    private String userRole;

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
