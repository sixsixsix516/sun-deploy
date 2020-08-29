package com.sixsixsix516.ssh;

/**
 * @author sun 2020/8/28 17:15
 */

import com.jcraft.jsch.UserInfo;


public class MyUserInfo implements UserInfo  {

    private String password;

    @Override
    public String getPassphrase() {
        return null;
    }

    @Override
    public String getPassword() {
        return "server2008!@";
    }

    @Override
    public boolean promptPassword(String message) {
        return true;
    }

    @Override
    public boolean promptPassphrase(String message) {
        return true;
    }

    @Override
    public boolean promptYesNo(String message) {
        return true;
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
