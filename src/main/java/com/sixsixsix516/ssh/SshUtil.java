package com.sixsixsix516.ssh;

import com.jcraft.jsch.*;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author sun 2020/8/28 17:36
 */
public class SshUtil {

    public static void execute(String user, String password, String host, int port, String command) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(user, host, port);
        session.setPassword(password);
        MyUserInfo myUserInfo = new MyUserInfo();
        myUserInfo.setPassword(password);
        session.setUserInfo(myUserInfo);
        session.connect(30000);
        Channel channel = session.openChannel("shell");
        channel.setInputStream(new ByteArrayInputStream((command + " \n").getBytes()));
        channel.connect(3 * 1000);
    }

}
