package com.duzhou.zlibray.xuliehua;

import java.io.Serializable;

/**
 * Created by zhou on 16-4-23.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 32092119900927235L;
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
