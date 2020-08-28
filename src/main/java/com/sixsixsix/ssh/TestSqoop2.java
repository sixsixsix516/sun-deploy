package com.sixsixsix.ssh;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.InputStream;

/**
 * 实时Bash
 *
 * @author sun 2020/8/28 17:10
 */
public class TestSqoop2 {


    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String HOST = "";
    private static final int DEFAULT_SSH_PORT = 12640;

    public static void main(String[] args) throws Exception {

        String command = "/home/superman/deploy.sh";

        JSch jsch = new JSch();
        Session session = jsch.getSession(USER, HOST, DEFAULT_SSH_PORT);
        session.setPassword(PASSWORD);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect(60 * 1000);
        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(command);
        channel.setInputStream(null);
        ((ChannelExec) channel).setErrStream(System.err);
        InputStream in = channel.getInputStream();
        channel.connect();

        byte[] tmp = new byte[1024];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0) break;
                System.out.print(new String(tmp, 0, i));
            }
            if (channel.isClosed()) {
                if (in.available() > 0) continue;
                System.out.println("exit-status: " + channel.getExitStatus());
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception ee) {
            }
        }
        channel.disconnect();
        session.disconnect();
    }


}
