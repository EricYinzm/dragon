package com.wdcloud.jyx;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;

import com.wdcloud.boot.wall.security.EnableHsession;
import com.wdcloud.boot.wall.security.SessionValidateController;

/**
 * 文件名称： com.wdcloud.jyx.JyxWebApp.java</br>
 * 初始作者： huxh</br>
 * 创建日期： 2017年8月9日</br>
 * 功能说明： 这里用一句话描述这个类的作用--此句话需删除 <br/>
 * =================================================<br/>
 * 修改记录：<br/>
 * 修改作者 日期 修改内容<br/>
 * ================================================<br/>
 * Copyright (c) 2010-2011 .All rights reserved.<br/>
 */
@SpringCloudApplication
@EnableHsession
@ComponentScan(basePackages = { "com.wdcloud.boot.core", "com.wdcloud.boot.wall", "com.wdcloud.jyx" }, basePackageClasses = SessionValidateController.class)
public class JyxWebApp {

	public static void main(String[] args) throws Exception {

		SpringApplication.run(JyxWebApp.class, args);
	}

}
