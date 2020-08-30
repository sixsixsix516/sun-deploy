# sun-deploy

将SpringBoot程序一键上传到远程服务器并运行



## 使用方式
#### 1.引入插件

```xml
<plugin>
      <groupId>com.sixsixsix516</groupId>
      <artifactId>sun-deploy-maven-plugin</artifactId>
      <version>1.0</version>
      <configuration>
          <ip>输入你的服务器ip</ip>
          <port>输入你的服务器端口(默认值22)</port>
          <user>输入你的服务器用户名</user>
          <password>输入你的服务器密码</password>
          <deployPath>服务器部署路径(不存在会自动创建)</deployPath>
      </configuration>
  </plugin>
```

#### 2.1 使用idea运行

![idea方式](http://img.chaorenaz.com/6879.png) 
