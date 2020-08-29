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

package com.sixsixsix516.mojo;

import com.jcraft.jsch.JSchException;
import com.sixsixsix516.SftpUtil;
import com.sixsixsix516.ssh.SshUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.*;

/**
 * @author sun 2020/8/29 11:39
 */
@Execute(phase = LifecyclePhase.PACKAGE)
@Mojo(name = "deploy")
public class DeployMojo extends AbstractMojo {

    @Parameter
    private String ip;
    @Parameter(defaultValue = "22")
    private Integer port;
    @Parameter
    private String user;
    @Parameter
    private String password;
    /**
     * 部署路径
     */
    @Parameter
    private String deployPath;
    /**
     * jar文件名称
     */
    @Parameter(defaultValue = "${project.artifactId}-${project.version}.${project.packaging}")
    private String fileName;

    /**
     * 项目根路径
     */
    @Parameter(defaultValue = "${user.dir}")
    private String projectPath;


    @Override
    public void execute() {
        // 0.数据校验
        // 1.上传文件
        SftpUtil sftpUtil = new SftpUtil(user, password, ip, port);
        try {
            sftpUtil.login();
        } catch (JSchException e) {
            getLog().error("服务器连接失败 !", e);
            return;
        }
        try {
            sftpUtil.upload(deployPath, fileName, new FileInputStream(new File(projectPath + "/target/" + fileName)));
        } catch (Exception e) {
            getLog().error("未找到部署的包 : " + fileName);
            return;
        }
        sftpUtil.logout();
        // 2.关闭端口
        try {
            SshUtil.execute(user, password, ip, port, "kill -9 `jps -l | grep '" + deployPath + fileName + "' | cut -d ' ' -f 1 `");
            SshUtil.execute(user, password, ip, port, "nohup java -jar " + deployPath + fileName + " &");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
