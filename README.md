# Spring-cloud-framework

#### 项目结构

基础模块分为5个模块：

- DISCOVERY-SERVICE：服务发现模块，使用**spring cloud eureka**服务发现及注册中心组件
- API-GATEWAY-SERVICE：对外网关模块，使用**spring cloud zuul**网关组件
- AUTH-SERVICE：授权认证模块，使用**spring cloud Oauth2、spring cloud security、JWT**实现授权认证
- MONITOR-SERVICE：监控模块，使用**Zipkin**监控服务间访问链路情况，使用**Turbine**收集每个服务的请求情况

#### 启动顺序

1. 启动DISCOVERY-SERVICE，服务监听端口为**8500**
2. 启动AUTH-SERVICE，服务监听端口为**8081**
3. 启动API-GATEWAY-SERVICE，服务监听端口为**8082**
5. 启动MONITOR-SERVICE，服务监听端口为**8084**

#### 特别说明

基础模块里`auth`模块用的数据库是基于内存的H2数据库，实际开发中根据需要换成`MySQL`、`Oracle`等，启动完毕之后，可访问`http://localhost:8081/h2`，需登录访问：

**Driver Class**: `org.h2.Driver`

**JDBC URL**: `jdbc:h2:mem:auth`

**User Name**: `sa`

**Password**: `sa`

#### 授权认证

第一步，先获取`access_token`

```shell
$ curl -X'POST' -H'Authorization:Basic Y2xpZW50OnRlc3Q=' -d 'username=joseph&password=123&grant_type=password' http://localhost:8082/auth/oauth/token
```

授权信息是`client:test`的base64编码

第二步，访问业务服务，如

```shell
$ curl -X'GET' -H 'Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1Njk0MTQwMzEsInVzZXJfbmFtZSI6Impvc2VwaCIsImF1dGhvcml0aWVzIjpbImFkbWluIl0sImp0aSI6IjMyNDg5NjhmLTQzNTktNDEzMy04OTNhLWZkNDEzNGUwMWQ3NyIsImNsaWVudF9pZCI6ImNsaWVudCIsInNjb3BlIjpbImFkbWluIl19.DjLjlwTRkK4mICIkxCgGAtPvCsoPTuI1ncFUuJwTOipu8O5A45ujd9QSFxbUlLCr8lZEo5yBqIXdTl5DuBPH_Ndnw-je2-wBNgAEztT3anv6BnJgQHxQ8ytds0Yhukn12N3eLxvKiKYFW5flPCNeEuh94m6AY2EXjnQ-dcfowPY' http://localhost:8082/auth/test
```

得到响应：

```
Authorization test...
```

如果`access_token`失效，不想使用用户名密码方式获取`access_token`的话，可以使用`refresh_token`获取

```shell
$ curl -X'POST' -H'Authorization:Basic Y2xpZW50OnRlc3Q=' -H 'Content-Type: application/x-www-form-urlencoded' -d 'refresh_token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJqb3NlcGgiLCJzY29wZSI6WyJhZG1pbiJdLCJhdGkiOiIxOGE3NmE0Zi0zODM0LTQ2N2UtYWM5OS1hYmZmNGUzN2FjMmMiLCJleHAiOjE1Njk4MjI3NzQsImF1dGhvcml0aWVzIjpbImFkbWluIl0sImp0aSI6ImVlMWYzZGU2LTc1ZWUtNGQ2Yi1iZjJlLWIzM2M0MTc2NTRiZCIsImNsaWVudF9pZCI6ImNsaWVudCJ9.jCmA5-9tqvEcuzjFsyUFgLkpnSS9KbvjO1nU7K0ducczvhvVevZ1PXVzVj4tn54sHPIWckpqIFF2CjQ7Lmx_au3BqiXdrAc2TdaeZBdbilU81lt-jX_BEa4eY5r2yOzMMUeWXJgbxFYFFqWUud3mobywYdPwjG_ReDjIRnvjmZg&grant_type=refresh_token' http://localhost:8082/auth/oauth/token
```



#### 服务Hystrix-Dashboard地址

http://ip:port/hystrix

#### Zipkin访问地址

http://localhost:8084/zipkin

#### Turbine访问地址

http://localhost:8084/hytrix

输入以下信息，然后点击“Monitor Stream”，对服务进行调用（使用网关入口进行访问）

![turbine](./turbine.png)

通过请求`gateway`获取`access_token`时，会捕获到请求链路信息

*zipkin*

![](./zipkin.png)

*turbine*

![](./monitor.png)

#### TODO

1. `JWT`使用`redis`作为存储，而不是频繁查询数据库放置内存中，提高性能。
2. `eureka`采用的是`CAP`中的`AP`，在实际开发中需要业务数据一致性，因此应当使用`zookeeper`、`consul`等服务注册发现组件。
3. 提供一些基础性组件，像`MQ消息队列`、`文件操作`、`缓存工具`等等。
4. 开发具体业务时，避免不了要跨服务调用，需要加入`Feign`组件进行`http`通信调用。