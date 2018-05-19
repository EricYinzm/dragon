package com.wdcloud.poi.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件名称： com.wdcloud.poi.test.DictData.java</br>
 * 初始作者： yinzhaomin</br>
 * 创建日期： 2017年8月29日</br>
 * 功能说明： 这里用一句话描述这个类的作用--此句话需删除 <br/>
 * =================================================<br/>
 * 修改记录：<br/>
 * 修改作者 日期 修改内容<br/>
 * ================================================<br/>
 * Copyright (c) 2010-2011 .All rights reserved.<br/>
 */
public class DictData {

	public static Map<String, Object>	dict	= null;
	static {
		dict = new HashMap<>();
		List<String> list = new ArrayList<>();
		list.add("小学");
		list.add("初中");
		list.add("高中");
		dict.put("xd-dict", list);
		Map<String, List<String>> subMap = new HashMap<>();
		list = new ArrayList<>();
		list.add("语文");
		list.add("数学");
		subMap.put("小学", list);
		list = new ArrayList<>();
		list.add("物理");
		list.add("化学");
		subMap.put("初中", list);
		list = new ArrayList<>();
		list.add("历史");
		list.add("地理");
		subMap.put("高中", list);
		dict.put("xk-dict", subMap);
	}

	/** 获取数据字典中的值 */
	public static Object getDict(String dict) {

		return DictData.dict.get(dict);
	}
}
