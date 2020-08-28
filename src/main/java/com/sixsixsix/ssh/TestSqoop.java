package com.sixsixsix.ssh;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

/**
 * 实时Bash
 *
 * @author sun 2020/8/28 17:10
 */
public class TestSqoop {


    private static final String USER = "";
    private static final String PASSWORD = "";
    private static final String HOST = "";
    private static final int DEFAULT_SSH_PORT = 12640;

    public static void main(String[] arg) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(USER, HOST, DEFAULT_SSH_PORT);
            session.setPassword(PASSWORD);

            session.setUserInfo(new MyUserInfo());
            session.connect(30000);   // making a connection with timeout.
            Channel channel = session.openChannel("shell");
            channel.setInputStream(System.in);
            channel.setOutputStream(System.out);
            //channel.connect();
            channel.connect(3 * 1000);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
