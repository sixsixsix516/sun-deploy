/*
 * Copyright (c) 2011-2020, sun (sixsixsix516@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.sixsixsix516;

import com.jcraft.jsch.*;
import java.io.*;
import java.util.Properties;


public class SftpUtil {

    private ChannelSftp sftp;

    private Session session;
    /**
     * FTP 登录用户名
     */
    private String username;
    /**
     * FTP 登录密码
     */
    private String password;
    /**
     * FTP 服务器地址IP地址
     */
    private String host;
    /**
     * FTP 端口
     */
    private int port;


    /**
     * 构造基于密码认证的sftp对象
     */
    public SftpUtil(String username, String password, String host, int port) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }


    /**
     * 连接sftp服务器
     */
    public void login() throws JSchException {
        JSch jsch = new JSch();
        session = jsch.getSession(username, host, port);
        if (password != null) {
            session.setPassword(password);
        }
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
        Channel channel = session.openChannel("sftp");
        channel.connect();
        sftp = (ChannelSftp) channel;
    }

    /**
     * 关闭连接 server
     */
    public void logout() {
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
            }
        }
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }

    /**
     * 将输入流的数据上传到sftp作为文件
     *
     * @param directory    上传到该目录
     * @param sftpFileName sftp端文件名
     * @param input        输入流
     */
    public void upload(String directory, String sftpFileName, InputStream input) throws SftpException {
        try {
            sftp.cd(directory);
        } catch (SftpException e) {
            sftp.mkdir(directory);
            sftp.cd(directory);
        }
        sftp.put(input, sftpFileName);
    }


}
