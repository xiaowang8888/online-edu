# 谷粒学院-在线教育项目简介

## 一、项目描述

谷粒商城，是一个职业技能在线教育系统，为用户提供线上学习的环境和资源，使用了微服务技术架构，采用前后端分离开发的方式，分为**前台用户系统**和**后台运营平台**。

- 后端的主要技术架构是：SpringBoot + SpringCloud + MyBatis-Plus + MySQL + HttpClient+ Maven + EasyExcel + nginx
- 前端的架构是：Node.js + Vue.js +element-ui + NUXT + ECharts
- 其他涉及到的中间件包括Redis、阿里云OSS、阿里云视频点播、阿里云短信服务
- 业务中使用了ECharts做图表展示，使用EasyExcel完成课程分类批量添加

## 二、系统架构

![image-20210608202711865](http://guli-edu-avator1.oss-cn-shenzhen.aliyuncs.com/picBed/image-20210608202711865.png)



## 三、系统模块

![image-20210606095809860](http://guli-edu-avator1.oss-cn-shenzhen.aliyuncs.com/picBed/image-20210606095809860.png)



## 四、项目模块概览

**guli_parent：后端父工程**

- **common**：公共模块父节点
  - **common-utils**：工具类模块，所有模块都可以依赖于它
  - **service-base**：service服务的base包，包含service服务的公共配置类，所有service模块依赖于它
  - **spring-security**：认证与授权模块，需要认证授权的service服务依赖于它

- **infrastructure**：基础服务模块父节点
  - **api-gateway**：api网关服务
- **service**：api接口服务父节点
  - **service-acl**：用户权限管理api接口服务（用户管理、角色管理和权限管理等）
  - **service-cms**：cms api接口服务
  - **service-edu**：教学相关api接口服务
  - **service-msm**：短信api接口服务
  - **service-order**：订单相关api接口服务
  - **service-oss**：阿里云oss api接口服务
  - **service-statistics**：统计报表api接口服务
  - **service-ucenter**：会员api接口服务
  - **service-vod**：视频点播api接口服务

**guli_front：前端项目文件夹**

- **guli_admin**：运营管理前端工程
- **guli_client**：用户系统前端工程









