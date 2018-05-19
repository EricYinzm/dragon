package com.wdcloud.jyx.web.system.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件名称： com.wdcloud.jyx.web.system.util.CommonUtils.java</br>
 * 初始作者： wangpengju</br>
 * 创建日期： 2017年8月21日</br>
 * 功能说明： 这里用一句话描述这个类的作用--此句话需删除 <br/>
 * =================================================<br/>
 * 修改记录：<br/>
 * 修改作者 日期 修改内容<br/>
 * ================================================<br/>
 * Copyright (c) 2010-2011 .All rights reserved.<br/>
 */
public class CommonUtils {

	// @Autowired
	// static SessionUserHolder sessionUserHolder;

	/**
	 * 方法描述: [将bean转为map.]</br>
	 * 初始作者: 王鹏举 <br/>
	 * 创建日期: 2017年8月21日-下午2:49:22<br/>
	 * 开始版本: 2.0.0<br/>
	 * =================================================<br/>
	 * 修改记录：<br/>
	 * 修改作者 王鹏举 日期 修改内容<br/>
	 * ================================================<br/>
	 *
	 * @param bean
	 * @return
	 *         Map<String,Object>
	 */
	public static Map<String, Object> beantoMap(Object bean) {

		Map<String, Object> returnMap = new HashMap<String, Object>();
		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor descriptor : propertyDescriptors) {
				String propertyName = descriptor.getName();
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					Object result = readMethod.invoke(bean, new Object[0]);
					if (result != null) {
						returnMap.put(propertyName, result);
					} else {
						returnMap.put(propertyName, "");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}

	/**
	 * 方法描述: [将bean转为map[不存放空值].]</br>
	 * 初始作者: 王鹏举 <br/>
	 * 创建日期: 2017年8月21日-下午2:49:44<br/>
	 * 开始版本: 2.0.0<br/>
	 * =================================================<br/>
	 * 修改记录：<br/>
	 * 修改作者 王鹏举 日期 修改内容<br/>
	 * ================================================<br/>
	 *
	 * @param bean
	 * @return
	 *         Map<String,Object>
	 */
	public static Map<String, Object> beantoMapFilterEmpty(Object bean) {

		Map<String, Object> returnMap = new HashMap<String, Object>();
		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor descriptor : propertyDescriptors) {
				String propertyName = descriptor.getName();
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					Object result = readMethod.invoke(bean, new Object[0]);
					if (result != null) {
						returnMap.put(propertyName, result.toString());
					} else {
						// 不存放空值
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}

}
