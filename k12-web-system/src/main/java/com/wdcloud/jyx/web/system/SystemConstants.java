package com.wdcloud.jyx.web.system;

import com.wdcloud.boot.core.util.ConfigurableContants;

/**
 * 文件名称： com.wdcloud.jyx.server.common.jcxxConstants.java</br>
 * 初始作者： zhangXin</br>
 * 创建日期： 2016年10月21日</br>
 * 功能说明： 这里用一句话描述这个类的作用--此句话需删除 <br/>
 * =================================================<br/>
 * 修改记录：<br/>
 * 修改作者 日期 修改内容<br/>
 * ================================================<br/>
 * Copyright (c) 2010-2011 .All rights reserved.<br/>
 */

public class SystemConstants extends ConfigurableContants {

	/**
	 * 是否代码（0-否，1-是）
	 */
	public static String	SYSTEM_SFDM_0						= getProperty("system.mb.sfdm.0", "0");
	/**
	 * 是否代码（0-否，1-是）
	 */
	public static String	SYSTEM_SFDM_1						= getProperty("system.mb.sfdm.1", "1");

	/**
	 * DEFLAG标识（A-新建、U-修改、D-删除）
	 */
	public static String	SYSTEM_PERSISTENCE_OPERATION_CREATE	= getProperty(
																		"system.persistence.operation.create.a",
																		"A");
	/**
	 * DEFLAG标识（A-新建、U-修改、D-删除）
	 */
	public static String	SYSTEM_PERSISTENCE_OPERATION_UPDATE	= getProperty(
																		"system.persistence.operation.update.u",
																		"U");
	/**
	 * DEFLAG标识（A-新建、U-修改、D-删除）
	 */
	public static String	SYSTEM_PERSISTENCE_OPERATION_DELETE	= getProperty(
																		"system.persistence.operation.delete.d",
																		"D");

	/**
	 * 服务层名字(serviceId)
	 */
	public static String	SYSTEM_SERVCER_SYSTEM				= getProperty("system.server.system",
																		"K12-SERVER-SYSTEM");
	/**
	 * 中小学课程代码
	 */
	public static String	CODECLASS_ZXXKCDM					= getProperty("jyx.codeclass.zxxkcdm", "ZXXKCDM");
	/**
	 * 学段代码
	 */
	public static String	CODECLASS_XDDM						= getProperty("jyx.codeclass.xddm", "XDDM");
	/**
	 * 性别代码
	 */
	public static String	CODECLASS_XBDM						= getProperty("jyx.codeclass.xbdm", "XBDM");
	/**
	 * 民族代码
	 */
	public static String	CODECLASS_MZDM						= getProperty("jyx.codeclass.mzdm", "MZDM");
}
