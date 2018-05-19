package com.wdcloud.jyx.web.system.commom;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.wdcloud.boot.core.i18n.I18nMessageSource;

/**
 * 文件名： com.wdcloud.jyx.common.util.LanguageUtil.java
 * 作者： huxh
 * 日期： 2017年6月19日
 * 功能说明：获取语言
 * =========================================================
 * 修改记录：
 * 修改作者 日期 修改内容
 * =========================================================
 * Copyright (c) 2010-2011 .All rights reserved.
 */
@Service
public class LanguageService {

	@Autowired
	private I18nMessageSource	i18nMessageSource;

	public String getLanguage(HttpServletRequest request) {

		String language = LocaleContextHolder.getLocale().getLanguage();
		return language;
	}
}
