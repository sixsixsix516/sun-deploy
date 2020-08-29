package com.sixsixsix516.mojo;

import com.jcraft.jsch.SftpException;
import com.sixsixsix516.SftpUtil;
import com.sixsixsix516.ssh.SshUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.*;

/**
 * @author sun 2020/8/29 11:39
 */
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
    @Parameter
    private String fileName;

    /**
     * 项目根路径
     */
    @Parameter
    private String projectPath;


    @Override
    public void execute() {
        // 0.数据校验
        // 1.上传文件
        SftpUtil sftpUtil = new SftpUtil(user, password, ip, port);
        sftpUtil.login();
        try {
            sftpUtil.upload(deployPath, fileName, new FileInputStream(new File(projectPath + "/target/" + fileName)));
        } catch (SftpException e) {
            getLog().info(e.getMessage());
        } catch (FileNotFoundException e) {
            getLog().error("上传文件不存在", e);
        }
        // 2.关闭端口
        try {
            SshUtil.execute(user, password, ip, port, "kill -9 `jps -l | grep '" + deployPath + fileName + "' | cut -d ' ' -f 1 `");
            SshUtil.execute(user, password, ip, port, "nohup java -jar " + deployPath + fileName + " &");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
