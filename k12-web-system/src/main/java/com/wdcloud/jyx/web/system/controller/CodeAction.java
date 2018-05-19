package com.wdcloud.jyx.web.system.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wdcloud.boot.core.util.AppUtil;

/**
 * 文件名称： com.wdcloud.jiaoyan.commom.syscode.controller.CodeAction.java</br>
 * 初始作者： xingqian</br>
 * 创建日期： 2016年8月9日</br>
 * 功能说明：码表Action <br/>
 * =================================================<br/>
 * 修改记录：<br/>
 * 修改作者 日期 修改内容<br/>
 * ================================================<br/>
 * Copyright (c) 2010-2011 .All rights reserved.<br/>
 */
@RestController
@RequestMapping("/web_jyy")
public class CodeAction {

	@Autowired
	private AppUtil		appUtil;

	@Autowired
	private Environment	env;

	@RequestMapping(value = "/codeclass")
	@ResponseBody
	public Object getCodeMapByCodeClassVersion(
			String clv,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String language = LocaleContextHolder.getLocale().getLanguage();
		String url = env.getProperty("wdcloud.k12.syscode.url") + "/system/syscode/getcodeclass";

		Map<String, Object> codeclass_map = (Map<String, Object>) JSON.parse(clv);
		List<Map<String, Object>> info_lst = new ArrayList<Map<String, Object>>();
		if (codeclass_map != null) {
			for (String codeclass : codeclass_map.keySet()) {

				Long version_now = -1L;
				Long version_param = Long.valueOf(String.valueOf(codeclass_map.get(codeclass)));
				LinkedHashMap<String, Object> codeClass_map = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("codeClass", codeclass);
				map.put("language", language);
				LinkedHashMap<String, JSONObject> sysCodes_map = appUtil.postForEntity(
						url, map,
						LinkedHashMap.class);
				List<LinkedHashMap<String, String>> data_lst = new ArrayList<LinkedHashMap<String, String>>();
				for (String code : sysCodes_map.keySet()) {
					LinkedHashMap<String, String> date_map = new LinkedHashMap<String, String>();
					version_now = sysCodes_map.get(code).getLong("versions");
					// if (version_param == -1 || version_param < version_now) {
					if (version_param.longValue() == -1L || version_param.longValue() < version_now.longValue()) {
						date_map.put("code", code);
						date_map.put("codedesc", sysCodes_map.get(code).getString("codedesc"));
						data_lst.add(date_map);
					}
				}
				if (data_lst.size() > 0) {
					codeClass_map.put("codeclass", codeclass);
					codeClass_map.put("version", version_now);
					codeClass_map.put("data", data_lst);
					info_lst.add(codeClass_map);
				}
			}
		}

		Map<String, Object> rs_map = new HashMap<String, Object>();
		rs_map.put("isSuccess", "true");
		if (info_lst.size() > 0) {
			rs_map.put("info", info_lst);
		}
		return rs_map;
	}
}
