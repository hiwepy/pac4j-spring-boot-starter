# pac4j-spring-boot-starter

pac4j starter for spring boot

### 组件简介

Pac4j 是一个支持多种协议多种框架的Java 权限引擎。

> 基于 Pac4j 4.x 的 Spring Boot Starter 实现

官方网站：https://www.pac4j.org/

### 使用说明

##### 1、Spring Boot 项目添加 Maven 依赖

``` xml
<dependency>
	<groupId>com.github.hiwepy</groupId>
	<artifactId>pac4j-spring-boot-starter</artifactId>
	<version>1.0.3.RELEASE</version>
</dependency>
```

##### 2、在`application.yml`文件中增加如下配置

```yaml
################################################################################################################  
###Pac4j 第三方登录（QQ、微信、易班、康赛）配置：  
################################################################################################################
pac4j:
  enabled: true
  default-client-name: cas
  callback-url: http://172.16.88.245:8088/smartedu-authz/authz/login/pac4j?client_name=uniauth&proxy=false
  callback-url-fixed: false
  client-parameter-name: client_name
  clients: cas
  service-url: http://172.16.88.245:8088/smartedu-authz
  logout:
    path-pattern: /**/logout/pac4j
  cas:
    enabled: true
    accept-any-proxy: true
    gateway: false
    login-url: https://127.0.0.1/sso/login
    logout-url: https://127.0.0.1/sso/logout
    prefix-url: https://127.0.0.1/sso/
    protocol: cas20-proxy
    renew: false
    # Cas客户端配置
    #cas-client: true
    #cas-client-name: cas
    # Cas代理客户端配置
    direct-cas-client: true
    direct-cas-client-name: cas
    #direct-cas-proxy-client: true
    #direct-cas-proxy-client-name: cas-proxy
  uniauth:
    enabled: true
    token:
      client-name: uniauth-token
      custom-params:
        syskey: xxxxxxxxxxxxxxxx
      encode-params: true
      profile-url: https://127.0.0.1:8080/yyxy_uniauth/ser/vaildTocken.action
      support-post-request: true
      support-get-request: true
      token-param-name: tocken
    signature:
      client-name: uniauth
  oauth:
    yiban:
      name: yiban
```

##### 3、使用示例

```java
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
    }

}

```

## Jeebiz 技术社区

Jeebiz 技术社区 **微信公共号**、**小程序**，欢迎关注反馈意见和一起交流，关注公众号回复「Jeebiz」拉你入群。

|公共号|小程序|
|---|---|
| ![](https://raw.githubusercontent.com/hiwepy/static/main/images/qrcode_for_gh_1d965ea2dfd1_344.jpg)| ![](https://raw.githubusercontent.com/hiwepy/static/main/images/gh_09d7d00da63e_344.jpg)|

