package com.sixsixsix;

import java.io.File;
import java.io.FileInputStream;

/**
 * 部署
 *
 * @author sun 2020/8/4 10:34
 */
public class Deploy {

    static String BASE_DIR = "/home/superman/";

    public static void main(String[] args) throws Exception {
        SftpUtil sftpUtil = new SftpUtil("root", "", "", 12640);
        sftpUtil.login();

        sftpUtil.upload(BASE_DIR + "manager/", "superman-manager.jar", new FileInputStream(new File("D:\\sun\\ideaproject\\superman\\superman-manager\\target\\superman-manager-1.0-SNAPSHOT.jar")));
        sftpUtil.upload(BASE_DIR + "master/", "superman-master.jar", new FileInputStream(new File("D:\\sun\\ideaproject\\superman\\superman-master\\target\\superman-master-1.0-SNAPSHOT.jar")));
        sftpUtil.upload(BASE_DIR + "user/", "superman-user.jar", new FileInputStream(new File("D:\\sun\\ideaproject\\superman\\superman-user\\target\\superman-user-1.0-SNAPSHOT.jar")));
        sftpUtil.logout();
    }
}
