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
