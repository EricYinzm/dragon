package com.wdcloud.jyx.web.system.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.jersey.api.client.Client;
import com.wdcloud.boot.core.model.ResponseModel;
import com.wdcloud.boot.core.util.AppUtil;
import com.wdcloud.boot.tool.DateUtil;
import com.wdcloud.jyx.web.system.SystemConstants;
import com.wdcloud.jyx.web.system.model.ExportDefinition;
import com.wdcloud.jyx.web.system.model.UserInfoModel;
import com.wdcloud.jyx.web.system.util.CommonUtils;
import com.wdcloud.jyx.web.system.util.ExcelUtil;
import com.wdcloud.jyx.web.system.util.PoiUtil;
import com.wdcloud.login.client.session.SessionUserHolder;

/**
 * 文件名称： com.wdcloud.jyx.web.system.controller.UserInfoAction.java</br>
 * 初始作者： wangpengju</br>
 * 创建日期： 2017年8月8日</br>
 * 功能说明： 教研员信息web接口类 <br/>
 * =================================================<br/>
 * 修改记录：<br/>
 * 修改作者 日期 修改内容<br/>
 * ================================================<br/>
 * Copyright (c) 2010-2011 .All rights reserved.<br/>
 */
@RestController
@RequestMapping("/web_jyy")
public class UserInfoAction {

	@Autowired
	SessionUserHolder		sessionUserHolder;
	@Autowired
	private Environment		env;
	@Autowired
	private AppUtil			appUtil;
	@Autowired
	private CodeAction		codeAction;
	private static Logger	log	= LoggerFactory.getLogger(UserInfoAction.class);

	/**
	 * 方法描述: 新增/修改教研员信息</br>
	 * 初始作者: 王鹏举 <br/>
	 * 创建日期: 2017年8月8日-下午3:56:50<br/>
	 * 开始版本: 2.0.0<br/>
	 * =================================================<br/>
	 * 修改记录：<br/>
	 * 修改作者 王鹏举 日期 修改内容<br/>
	 * ================================================<br/>
	 *
	 * @return
	 *         Object
	 */
	@RequestMapping(value = "/bcjyyxx", method = RequestMethod.POST)
	@ResponseBody
	public Object saveJyyxx(@RequestBody UserInfoModel userInfoModel, HttpServletRequest request) {

		ResponseModel responseModel = new ResponseModel();

		if (StringUtils.isNotBlank(userInfoModel.getOrgid()) && StringUtils.isNotBlank(userInfoModel.getSectioncode())
				&& StringUtils.isNotBlank(userInfoModel.getSubjectcode())
				&& StringUtils.isNotBlank(userInfoModel.getUsername())) {
			String loginid = sessionUserHolder.getLoginId(request);

			// TODO 测试账号
			// loginid = "2000245277";

			Map<String, Object> params_Map = new HashMap<String, Object>();
			Map<String, Object> params_jyy_Map = new HashMap<String, Object>();
			params_Map.put("id", userInfoModel.getOrgid());
			String jcxxserver_url = env.getProperty("wdcloud.jyx.jcxx.url");
			Map postForEntity = appUtil.postForEntity(
					jcxxserver_url + "/jcxx/selectorganizationbyid",
					params_Map, Map.class);
			if ("true".equals(postForEntity.get("isSuccess"))) {
				JSONObject jcbb = (JSONObject) postForEntity.get("rows");
				userInfoModel.setLocation(jcbb.getString("location"));
				userInfoModel.setLoginid(loginid);
				params_jyy_Map = CommonUtils.beantoMapFilterEmpty(userInfoModel);
				Map jyyEntity = appUtil.postForEntity(
						jcxxserver_url +
						"/jcxx/bcjyyxx",
						params_jyy_Map, Map.class);
				if ("true".equals(jyyEntity.get("isSuccess"))) {
					return jyyEntity;
				} else {
					responseModel.setIsSuccess("false");
					responseModel.setMsgCode("0003");
					return responseModel;
				}
			} else {
				responseModel.setIsSuccess("false");
				responseModel.setMsgCode("0002");
				return responseModel;
			}
		} else {
			responseModel.setIsSuccess("false");
			responseModel.setMsgCode("0001");
			return responseModel;
		}

	}

	/**
	 * 方法描述: 跟据用户id来得到用户详情信息</br>
	 * 初始作者: 王鹏举 <br/>
	 * 创建日期: 2017年8月8日-下午5:05:56<br/>
	 * 开始版本: 2.0.0<br/>
	 * =================================================<br/>
	 * 修改记录：<br/>
	 * 修改作者 王鹏举 日期 修改内容<br/>
	 * ================================================<br/>
	 *
	 * @param userInfoModel
	 * @param request
	 * @return
	 *         Object
	 */
	@RequestMapping(value = "/cxjyyxx", method = RequestMethod.GET)
	@ResponseBody
	public Object searchJyyXxById(UserInfoModel userInfoModel, HttpServletRequest request) {

		ResponseModel responseModel = new ResponseModel();
		if (StringUtils.isNotBlank(userInfoModel.getUserinfo_id())) {
			Map<String, Object> params_Map = new HashMap<String, Object>();
			String jcxxserver_url = env.getProperty("wdcloud.jyx.jcxx.url");
			params_Map.put("userinfo_id", userInfoModel.getUserinfo_id());
			Map responseEntity = appUtil.postForEntity(
					jcxxserver_url +
					"/jcxx/cxjyyxx",
					params_Map, Map.class);
			if ("true".equals(responseEntity.get("isSuccess"))) {
				return responseEntity;
			} else {
				responseModel.setIsSuccess("false");
				responseModel.setMsgCode("0002");
				return responseModel;
			}

		} else {
			responseModel.setIsSuccess("false");
			responseModel.setMsgCode("0001");
			return responseModel;
		}
	}

	/**
	 * 方法描述: 分页查询教研员信息列表</br>
	 * 初始作者: 王鹏举 <br/>
	 * 创建日期: 2017年8月8日-下午5:23:11<br/>
	 * 开始版本: 2.0.0<br/>
	 * =================================================<br/>
	 * 修改记录：<br/>
	 * 修改作者 王鹏举 日期 修改内容<br/>
	 * ================================================<br/>
	 *
	 * @param userInfoModel
	 * @param request
	 * @return
	 *         Object
	 */
	@RequestMapping(value = "/cxjyyxxlb", method = RequestMethod.GET)
	@ResponseBody
	public ResponseModel searchJyyxxlb(UserInfoModel userInfoModel, HttpServletRequest request) {

		ResponseModel responseModel = new ResponseModel();
		String language = LocaleContextHolder.getLocale().getLanguage();
		// 当前登录人账号
		String loginid = sessionUserHolder.getLoginId(request);

		// TODO 测试用
		// loginid = "2000244729";

		String jcxxserver_url = env.getProperty("wdcloud.jyx.jcxx.url");
		if (SystemConstants.SYSTEM_SFDM_1.equals(userInfoModel.getFlagType()) && userInfoModel.getRows() != null
				&& userInfoModel.getPage() != null
				|| SystemConstants.SYSTEM_SFDM_0.equals(userInfoModel.getFlagType())) {
			Map<String, Object> params_Map = new HashMap<String, Object>();

			if (StringUtils.isNotBlank(userInfoModel.getOrgid())) {
				params_Map.put("sfcorgid", "0");
				params_Map.put("orgid", userInfoModel.getOrgid());
			} else {
				params_Map.put("sfcorgid", "1");
				// 调server层根据当前登录人的账号查他的orgid
				Map<String, Object> orgMap = new HashMap<String, Object>();
				orgMap.put("loginid", loginid);
				Map responseOrg = appUtil.postForEntity(
						jcxxserver_url +
						"/jcxx/cxjyyOrgbyzh",
						orgMap, Map.class);
				if ("true".equals(responseOrg.get("isSuccess"))) {
					String orgid = (String) responseOrg.get("rows");
					params_Map.put("orgid", orgid);
				} else {
					responseModel.setIsSuccess("false");
					responseModel.setMsgCode("0003");
					return responseModel;
				}

			}
			params_Map.put("loginid", loginid);
			params_Map.put("flagType", userInfoModel.getFlagType());
			params_Map.put("rows", userInfoModel.getRows());
			params_Map.put("page", userInfoModel.getPage());
			params_Map.put("username", userInfoModel.getUsername() != null ? userInfoModel.getUsername() : "");
			params_Map.put("lrsj_start", userInfoModel.getLrsj_start() != null ? userInfoModel.getLrsj_start() : "");
			params_Map.put("lrsj_end", userInfoModel.getLrsj_end() != null ? userInfoModel.getLrsj_end() : "");
			params_Map.put("sfcjzh", userInfoModel.getSfcjzh() != null ? userInfoModel.getSfcjzh() : "");
			// params_Map.put("orgid", userInfoModel.getOrgid() != null ? userInfoModel.getOrgid() : "");
			ResponseModel responseEntity = appUtil.postForEntity(
					jcxxserver_url +
					"/jcxx/cxjyyxxlb",
					params_Map, ResponseModel.class);
			if ("true".equals(responseEntity.getIsSuccess())) {

				String url = env.getProperty("wdcloud.k12.syscode.url") + "/system/syscode/getcodeclass";
				Map<String, Object> xdMap = new HashMap<String, Object>();
				xdMap.put("codeClass", SystemConstants.CODECLASS_XDDM);
				xdMap.put("language", language);
				LinkedHashMap<String, Map<String, Object>> xdCodes_map = appUtil.postForEntity(
						url, xdMap,
						LinkedHashMap.class);

				Map<String, Object> xkMap = new HashMap<String, Object>();
				xkMap.put("codeClass", SystemConstants.CODECLASS_ZXXKCDM);
				xkMap.put("language", language);
				LinkedHashMap<String, Map<String, Object>> xkCodes_map = appUtil.postForEntity(
						url, xkMap,
						LinkedHashMap.class);

				List<Map<String, Object>> responseRows = (List<Map<String, Object>>) responseEntity.getRows();
				if (responseRows != null && responseRows.size() > 0) {
					for (Map<String, Object> map : responseRows) {
						String sectioncode = (String) map.get("sectioncode");
						String subjectcode = (String) map.get("subjectcode");
						String xdkm = "";
						String[] xds = sectioncode.split(",");
						String[] xks = subjectcode.split(",");
						if (xds.length == xks.length) {
							for (int i = 0; i < xks.length; i++) {
								String xd = xds[i];
								String xk = xks[i];
								if (xdCodes_map.containsKey(xd) && xkCodes_map.containsKey(xk)) {
									String xddesc = (String) xdCodes_map.get(xd).get("codedesc");
									String xkdesc = (String) xkCodes_map.get(xk).get("codedesc");
									xdkm += xddesc + xkdesc + "、";
								}
							}
						}
						if (xdkm.length() > 0) {
							map.put("xdkm", xdkm.substring(0, xdkm.length() - 1));
						} else {
							map.put("xdkm", "");
						}
					}

				}

				return responseEntity;
			} else {
				responseModel.setIsSuccess("false");
				responseModel.setMsgCode("0002");
				responseModel.setRows(new ArrayList<Map<String, Object>>());
				responseModel.setRecords("0");
				return responseModel;
			}

		} else {
			responseModel.setIsSuccess("false");
			responseModel.setMsgCode("0001");
			responseModel.setRows(new ArrayList<Map<String, Object>>());
			responseModel.setRecords("0");
			return responseModel;
		}

	}

	/**
	 * 方法描述: 创建账号</br>
	 * 初始作者: 王鹏举 <br/>
	 * 创建日期: 2017年8月8日-下午5:57:22<br/>
	 * 开始版本: 2.0.0<br/>
	 * =================================================<br/>
	 * 修改记录：<br/>
	 * 修改作者 王鹏举 日期 修改内容<br/>
	 * ================================================<br/>
	 *
	 * @param userInfoModel
	 * @param request
	 * @return
	 *         Object
	 */
	@RequestMapping(value = "/cjzh", method = RequestMethod.POST)
	@ResponseBody
	public Object createAccount(@RequestBody UserInfoModel userInfoModel, HttpServletRequest request) {

		ResponseModel responseModel = new ResponseModel();
		if (StringUtils.isNotBlank(userInfoModel.getPassword())) {
			Map<String, Object> params_Map = new HashMap<String, Object>();
			String jcxxserver_url = env.getProperty("wdcloud.jyx.jcxx.url");
			List<Map<String, String>> yxjyy = JSON.parseObject(userInfoModel.getYxjyy(), List.class);
			if (yxjyy != null && yxjyy.size() == 0) {
				UserInfoModel infoModel = new UserInfoModel();
				infoModel.setSfcjzh(SystemConstants.SYSTEM_SFDM_0);
				infoModel.setFlagType(SystemConstants.SYSTEM_SFDM_0);
				ResponseModel re = searchJyyxxlb(infoModel, request);
				if ("true".equals(re.getIsSuccess())) {
					List<Map<String, Object>> jyylb = (List<Map<String, Object>>) re.getRows();
					for (Map<String, Object> map : jyylb) {
						Map<String, String> jyyMap = new HashMap<String, String>();
						jyyMap.put("userinfo_id", (String) map.get("userinfo_id"));
						jyyMap.put("username", (String) map.get("username"));
						jyyMap.put("birthday", (String) map.get("birthday"));
						yxjyy.add(jyyMap);
					}
				}
			}

			params_Map.put("yxjyy", yxjyy);
			params_Map.put("password", userInfoModel.getPassword());
			Map responseEntity = appUtil.postForEntity(
					jcxxserver_url +
					"/jcxx/cjzh",
					params_Map, Map.class);
			if ("true".equals(responseEntity.get("isSuccess"))) {
				return responseEntity;
			} else {
				responseModel.setIsSuccess("false");
				responseModel.setMsgCode("0002");
				return responseModel;
			}
		} else {
			responseModel.setIsSuccess("false");
			responseModel.setMsgCode("0001");
			return responseModel;
		}

	}

	/**
	 * 方法描述:查询组织机构树</br>
	 * 初始作者: 王鹏举 <br/>
	 * 创建日期: 2017年8月8日-下午6:20:25<br/>
	 * 开始版本: 2.0.0<br/>
	 * =================================================<br/>
	 * 修改记录：<br/>
	 * 修改作者 王鹏举 日期 修改内容<br/>
	 * ================================================<br/>
	 *
	 * @param userInfoModel
	 * @param request
	 * @return
	 *         Object
	 */
	@RequestMapping(value = "/cxzzjgs", method = RequestMethod.POST)
	@ResponseBody
	public Object selectOrgTree(String pid, HttpServletRequest request) {

		ResponseModel responseModel = new ResponseModel();

		String loginId = sessionUserHolder.getLoginId(request);

		// TODO 测试账号
		// loginId = "2000251859";

		Map<String, Object> params_Map = new HashMap<String, Object>();
		String jcxxserver_url = env.getProperty("wdcloud.jyx.jcxx.url");
		params_Map.put("orgid", pid);
		params_Map.put("loginId", loginId);
		Map responseEntity = appUtil.postForEntity(
				jcxxserver_url +
				"/jcxx/cxzzjgs",
				params_Map, Map.class);
		if ("true".equals(responseEntity.get("isSuccess"))) {
			return responseEntity;
		} else {
			responseModel.setIsSuccess("false");
			responseModel.setMsgCode("0002");
			return responseModel;
		}

	}

	/**
	 * 方法描述: 删除教研员</br>
	 * 初始作者: 王鹏举 <br/>
	 * 创建日期: 2017年8月8日-下午7:06:35<br/>
	 * 开始版本: 2.0.0<br/>
	 * =================================================<br/>
	 * 修改记录：<br/>
	 * 修改作者 王鹏举 日期 修改内容<br/>
	 * ================================================<br/>
	 *
	 * @return
	 *         Object
	 */
	@RequestMapping(value = "/scjyy", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteJyy(@RequestBody UserInfoModel userInfoModel, HttpServletRequest request) {

		ResponseModel responseModel = new ResponseModel();
		String loginid = sessionUserHolder.getLoginId(request);

		// TODO 测试账号
		// loginid = "2000245277";

		if (StringUtils.isNotBlank(userInfoModel.getUserinfo_id())) {
			Map<String, Object> params_Map = new HashMap<String, Object>();
			String jcxxserver_url = env.getProperty("wdcloud.jyx.jcxx.url");
			params_Map.put("loginid", loginid);
			params_Map.put("userinfo_id", userInfoModel.getUserinfo_id());
			Map responseEntity = appUtil.postForEntity(
					jcxxserver_url +
					"/jcxx/scjyy",
					params_Map, Map.class);
			if ("true".equals(responseEntity.get("isSuccess"))) {
				return responseEntity;
			} else {
				responseModel.setIsSuccess("false");
				responseModel.setMsgCode("0002");
				return responseModel;
			}
		} else {
			responseModel.setIsSuccess("false");
			responseModel.setMsgCode("0001");
			return responseModel;
		}

	}

	/**
	 * 方法描述: 重置密码</br>
	 * 初始作者: 王鹏举 <br/>
	 * 创建日期: 2017年8月9日-上午10:01:00<br/>
	 * 开始版本: 2.0.0<br/>
	 * =================================================<br/>
	 * 修改记录：<br/>
	 * 修改作者 王鹏举 日期 修改内容<br/>
	 * ================================================<br/>
	 *
	 * @param userInfoModel
	 * @param request
	 * @return
	 *         Object
	 */
	@RequestMapping(value = "/czmm", method = RequestMethod.POST)
	@ResponseBody
	public Object resetPassword(@RequestBody UserInfoModel userInfoModel, HttpServletRequest request) {

		ResponseModel responseModel = new ResponseModel();
		if (StringUtils.isNotBlank(userInfoModel.getAccount())
				&& StringUtils.isNotBlank(userInfoModel.getNew_password())) {
			String account = userInfoModel.getAccount();
			String accountArr[] = account.split(",");
			Client client = Client.create();
			String serverUrl = env.getProperty("wdcloud.jyx.uic.server.url");
			boolean boo = true;
			for (String element : accountArr) {
				Map<String, Object> rcMap = new HashMap<String, Object>();
				Map<String, Object> rs = new HashMap<String, Object>();
				rcMap.put("loginid", element);
				rcMap.put("newpassword", userInfoModel.getNew_password());
				rcMap.put("passwordstrengthcheck", SystemConstants.SYSTEM_SFDM_0);

				rs = client.resource(serverUrl + "/rest/v1/users/password/updateByLoginID")
						.type(MediaType.APPLICATION_JSON)
						.post(Map.class, rcMap);
				if (!"1".equals(rs.get("result"))) {
					boo = false;
				}
			}
			if (boo) {
				responseModel.setIsSuccess("true");
				responseModel.setMsgCode("");
				return responseModel;
			} else {
				responseModel.setIsSuccess("false");
				responseModel.setMsgCode("0002");
				return responseModel;
			}
		} else {
			responseModel.setIsSuccess("false");
			responseModel.setMsgCode("0001");
			return responseModel;
		}

	}

	/**
	 * 方法描述: 根据学段查学科</br>
	 * 初始作者: 王鹏举 <br/>
	 * 创建日期: 2017年8月10日-上午11:11:54<br/>
	 * 开始版本: 2.0.0<br/>
	 * =================================================<br/>
	 * 修改记录：<br/>
	 * 修改作者 王鹏举 日期 修改内容<br/>
	 * ================================================<br/>
	 *
	 * @param userInfoModel
	 * @param request
	 * @return
	 *         Object
	 */
	@RequestMapping(value = "/gjxdhdxk", method = RequestMethod.GET)
	@ResponseBody
	public Object searchXkByXd(UserInfoModel userInfoModel, HttpServletRequest request) {

		ResponseModel responseModel = new ResponseModel();
		if (StringUtils.isNotBlank(userInfoModel.getXd())) {

			Map<String, Object> params_Map = new HashMap<String, Object>();

			params_Map.put("xd", userInfoModel.getXd());
			String jcxxserver_url = env.getProperty("wdcloud.jyx.jcxx.url");
			Map postForEntity = appUtil.postForEntity(
					jcxxserver_url +
					"/jcxx/cxkcbxd",
					params_Map, Map.class);
			if ("true".equals(postForEntity.get("isSuccess"))) {
				return postForEntity;
			} else {
				responseModel.setIsSuccess("false");
				responseModel.setMsgCode("0002");
				return responseModel;
			}
		} else {
			responseModel.setIsSuccess("false");
			responseModel.setMsgCode("0001");
			return responseModel;
		}
	}

	/**
	 * 方法描述: 校验用户是否登录</br>
	 * 初始作者: 王鹏举 <br/>
	 * 创建日期: 2017年8月23日-上午10:44:33<br/>
	 * 开始版本: 2.0.0<br/>
	 * =================================================<br/>
	 * 修改记录：<br/>
	 * 修改作者 王鹏举 日期 修改内容<br/>
	 * ================================================<br/>
	 *
	 * @param request
	 * @return
	 *         boolean
	 */
	@RequestMapping(value = "/ifLogin", method = RequestMethod.GET)
	@ResponseBody
	public ResponseModel checkIsLogin(HttpServletRequest request) {

		ResponseModel responseModel = new ResponseModel();
		Boolean isLogin = sessionUserHolder.isLogined(request);
		if (isLogin) {
			responseModel.setIsSuccess("true");
		} else {
			responseModel.setIsSuccess("false");
		}
		return responseModel;
	}

	/**
	 * 方法描述: 下载教研员模板</br>
	 * 初始作者: yinzhaomin<br/>
	 * 创建日期: 2017年8月28日-下午2:18:09<br/>
	 * 开始版本: 2.0.0<br/>
	 * =================================================<br/>
	 * 修改记录：<br/>
	 * 修改作者 日期 修改内容<br/>
	 * ================================================<br/>
	 *
	 * @param modelname
	 * @param response
	 * @param request
	 *            void
	 */
	@RequestMapping(value = "/xzjyymb", method = RequestMethod.POST)
	@ResponseBody
	public void downlodModel(String modelname, HttpServletResponse response, HttpServletRequest request) {

		Map<Integer, List<String>> dataMap = new HashMap<Integer, List<String>>();

		// 读取码表
		String language = LocaleContextHolder.getLocale().getLanguage();
		String url = env.getProperty("wdcloud.k12.syscode.url") + "/system/syscode/getcodeclass";

		// 性别
		List<String> xblist = new ArrayList<String>();
		Map<String, Object> xbMap = new HashMap<String, Object>();
		xbMap.put("codeClass", SystemConstants.CODECLASS_XBDM);
		xbMap.put("language", language);
		LinkedHashMap<String, Map<String, Object>> xbCodes_map = appUtil.postForEntity(
				url, xbMap,
				LinkedHashMap.class);
		for (String code : xbCodes_map.keySet()) {
			xblist.add((String) xbCodes_map.get(code).get("codedesc"));
		}
		dataMap.put(1, xblist);

		// 民族
		List<String> mzlist = new ArrayList<String>();
		Map<String, Object> mzMap = new HashMap<String, Object>();
		mzMap.put("codeClass", SystemConstants.CODECLASS_MZDM);
		mzMap.put("language", language);
		LinkedHashMap<String, Map<String, Object>> mzCodes_map = appUtil.postForEntity(
				url, mzMap,
				LinkedHashMap.class);
		for (String code : mzCodes_map.keySet()) {
			mzlist.add((String) mzCodes_map.get(code).get("codedesc"));
		}
		dataMap.put(2, mzlist);

		// 组织结构
		List<String> orglist = new ArrayList<String>();
		String loginId = sessionUserHolder.getLoginId(request);
		// TODO 测试账号
		// loginId = "2000251859";
		getAllOrg(loginId, "", orglist);

		dataMap.put(4, orglist);

		// 学段
		List<String> xdlist = new ArrayList<String>();
		Map<String, List<String>> subMap = new HashMap<>();
		Map<String, Object> xdMap = new HashMap<String, Object>();
		xdMap.put("codeClass", SystemConstants.CODECLASS_XDDM);
		xdMap.put("language", language);
		LinkedHashMap<String, Map<String, Object>> xdCodes_map = appUtil.postForEntity(
				url, xdMap,
				LinkedHashMap.class);
		for (String code : xdCodes_map.keySet()) {
			xdlist.add((String) xdCodes_map.get(code).get("codedesc"));

			Map<String, Object> params_Map = new HashMap<String, Object>();
			params_Map.put("xd", code);
			String jcxxserver_url = env.getProperty("wdcloud.jyx.jcxx.url");
			Map postForEntity = appUtil.postForEntity(
					jcxxserver_url +
					"/jcxx/cxkcbxd",
					params_Map, Map.class);
			if ("true".equals(postForEntity.get("isSuccess"))) {
				List<Map<String, Object>> xkList = (List<Map<String, Object>>) postForEntity.get("rows");
				if (xkList != null && xkList.size() > 0) {
					List<String> list = new ArrayList<String>();
					for (Map<String, Object> map : xkList) {
						list.add((String) map.get("kmdesc"));
					}
					subMap.put((String) xdCodes_map.get(code).get("codedesc"), list);
				}
			}

		}

		try {
			setExportResponseHeader(response,
					modelname + "_" + DateUtil.dateToString(new Date(), DateUtil.FMT_DATE_YYYYMMDDHHmmss) + ".xls");
			ServletOutputStream ouputStream = response.getOutputStream();
			List<ExportDefinition> edList = new ArrayList<>();
			// if ("zh".equals(language)) {
			edList.add(new ExportDefinition("姓名", "username", null, null, null));
			edList.add(new ExportDefinition("性别", "gender", null, null, null));
			edList.add(new ExportDefinition("民族", "nation", null, null, null));
			edList.add(new ExportDefinition("出生日期(YYYYMMDD)", "birthday", null, null, null));
			edList.add(new ExportDefinition("组织机构", "org", null, null, null));
			edList.add(new ExportDefinition("手机号", "phone", null, null, null));
			edList.add(new ExportDefinition("邮箱", "email", null, null, null));
			edList.add(new ExportDefinition("学段", "xd", "xd-dict", "xk-dict", "xk"));
			edList.add(new ExportDefinition("学科", "xk", "xk-dict", "", ""));

			// } else {
			// edList.add(new ExportDefinition("Username", "username", null, null, null));
			// edList.add(new ExportDefinition("Gender", "gender", null, null, null));
			// edList.add(new ExportDefinition("Nation", "nation", null, null, null));
			// edList.add(new ExportDefinition("Birthday(YYYYMMDD)", "birthday", null, null, null));
			// edList.add(new ExportDefinition("Organization", "org", null, null, null));
			// edList.add(new ExportDefinition("Phone", "phone", null, null, null));
			// edList.add(new ExportDefinition("Email", "email", null, null, null));
			// edList.add(new ExportDefinition("Section", "xd", "xd-dict", "xk-dict", "xk"));
			// edList.add(new ExportDefinition("Subject", "xk", "xk-dict", "", ""));
			// }

			Map<String, Object> dictData = new HashMap<String, Object>();
			dictData.put("xd-dict", xdlist);
			dictData.put("xk-dict", subMap);
			// ExcelUtil.downLoadModle("template/JYYGL_PLDRMB.xls", ouputStream,
			// dataMap);
			PoiUtil.downloadModel(ouputStream, edList, dataMap, dictData);
		} catch (IOException e) {
			log.error("~~~~~~~~~~~~~批量导入模板下载异常downlodModel", e);
		}
	}

	/**
	 * 方法描述: 查询所有组织机构</br>
	 * 初始作者: yinzhaomin<br/>
	 * 创建日期: 2017年9月4日-上午10:30:12<br/>
	 * 开始版本: 2.0.0<br/>
	 * =================================================<br/>
	 * 修改记录：<br/>
	 * 修改作者 日期 修改内容<br/>
	 * ================================================<br/>
	 *
	 * @param loginId
	 * @param orgid
	 * @param orglist
	 *            void
	 */
	public void getAllOrg(String loginId, String orgid, List<String> orglist) {

		Map<String, Object> org_params_Map = new HashMap<String, Object>();
		String jcxxserver_url = env.getProperty("wdcloud.jyx.jcxx.url");
		org_params_Map.put("orgid", orgid);
		org_params_Map.put("loginId", loginId);
		Map responseEntity = appUtil.postForEntity(
				jcxxserver_url +
				"/jcxx/cxzzjgs",
				org_params_Map, Map.class);
		String isSuccess = (String) responseEntity.get("isSuccess");
		if ("true".equals(isSuccess)) {
			List<Map<String, Object>> rows = (List<Map<String, Object>>) responseEntity.get("rows");
			if (rows != null && rows.size() > 0) {
				for (Map<String, Object> map : rows) {
					orglist.add((String) map.get("text"));
					getAllOrg(loginId, (String) map.get("id"), orglist);
				}
			}
		}
	}

	/**
	 * 方法描述: 批量导入教研员</br>
	 * 初始作者: yinzhaomin<br/>
	 * 创建日期: 2017年8月30日-下午4:10:05<br/>
	 * 开始版本: 2.0.0<br/>
	 * =================================================<br/>
	 * 修改记录：<br/>
	 * 修改作者 日期 修改内容<br/>
	 * ================================================<br/>
	 *
	 * @param multiReq
	 * @param request
	 * @param response
	 *            void
	 */
	@RequestMapping(value = "/pldrjyy", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel importJyy(MultipartHttpServletRequest multiReq, HttpServletRequest request,
			HttpServletResponse response) {

		ResponseModel responseModel = new ResponseModel();
		String language = LocaleContextHolder.getLocale().getLanguage();
		Map<String, Object> reMap = new HashMap<String, Object>();

		InputStream fis1 = null;
		InputStream fis2 = null;
		List<Object[]> titleList = new ArrayList<Object[]>();
		List<Object[]> drList = new ArrayList<Object[]>();
		try {
			fis1 = multiReq.getFile("file").getInputStream();
			boolean jydr = ExcelUtil.readExcel2Obj(fis1, titleList, 0, 0, 0, 9);
			if (jydr) {

				if (titleList != null && titleList.size() > 0) {

					Object[] titleObjects = titleList.get(0);
					if (titleObjects != null && titleObjects.length > 0) {

						if ("姓名".equals(titleObjects[0].toString()) && "性别".equals(titleObjects[1].toString())
								&& "民族".equals(titleObjects[2].toString())
								&& "出生日期(YYYYMMDD)".equals(titleObjects[3].toString())
								&& "组织机构".equals(titleObjects[4].toString())
								&& "手机号".equals(titleObjects[5].toString())
								&& "邮箱".equals(titleObjects[6].toString())
								&& "学段".equals(titleObjects[7].toString())
								&& "学科".equals(titleObjects[8].toString())) {
							fis2 = multiReq.getFile("file").getInputStream();
							boolean readTitleExcel = ExcelUtil.readExcel2Obj(fis2, drList, 0, 1, 0, 9);
							if (readTitleExcel) {
								// log.debug("~~~~~~~~~~~~~~导入：" + JSON.toJSONString(drList));
								if (drList != null && drList.size() > 0) {

									String loginid = sessionUserHolder.getLoginId(request);
									// loginid = "2000244729";
									String jcxxserver_url = env.getProperty("wdcloud.jyx.jcxx.url");
									Map<String, Object> paramMap = new HashMap<String, Object>();
									paramMap.put("loginid", loginid);
									paramMap.put("jyyList", drList);
									paramMap.put("language", language);
									responseModel = appUtil.postForEntity(
											jcxxserver_url +
											"/jcxx/plbcjyy",
											paramMap, ResponseModel.class);
									return responseModel;
								} else {
									responseModel.setIsSuccess("false");
									reMap.put("drrz", new ArrayList<Map<String, Object>>());
									reMap.put("drjg", "0");
									reMap.put("message", "2");
								}
							} else {
								responseModel.setIsSuccess("false");
								reMap.put("drrz", new ArrayList<Map<String, Object>>());
								reMap.put("drjg", "0");
								reMap.put("message", "0");
							}
						} else {
							responseModel.setIsSuccess("false");
							reMap.put("drrz", new ArrayList<Map<String, Object>>());
							reMap.put("drjg", "0");
							reMap.put("message", "1");
						}

					} else {
						responseModel.setIsSuccess("false");
						reMap.put("drrz", new ArrayList<Map<String, Object>>());
						reMap.put("drjg", "0");
						reMap.put("message", "1");
					}

				} else {
					responseModel.setIsSuccess("false");
					reMap.put("drrz", new ArrayList<Map<String, Object>>());
					reMap.put("drjg", "0");
					reMap.put("message", "2");
				}

			} else {
				responseModel.setIsSuccess("false");
				reMap.put("drrz", new ArrayList<Map<String, Object>>());
				reMap.put("drjg", "0");
				reMap.put("message", "0");
			}
		} catch (IOException e) {
			responseModel.setIsSuccess("false");
			reMap.put("drrz", new ArrayList<Map<String, Object>>());
			reMap.put("drjg", "0");
			reMap.put("message", "0");
			log.debug("~~~~~~~~~导入异常：" + JSON.toJSONString(drList), e);
		} finally {
			if (fis1 != null) {
				try {
					fis1.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fis2 != null) {
				try {
					fis2.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		reMap.put("drsj", DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm"));
		responseModel.setRows(reMap);
		return responseModel;
	}

	/**
	 * 方法描述: 导出教研员</br>
	 * 初始作者: yinzhaomin<br/>
	 * 创建日期: 2017年8月28日-下午2:22:32<br/>
	 * 开始版本: 2.0.0<br/>
	 * =================================================<br/>
	 * 修改记录：<br/>
	 * 修改作者 日期 修改内容<br/>
	 * ================================================<br/>
	 *
	 * @param response
	 * @param request
	 *            void
	 */
	@RequestMapping(value = "/dcjyy", method = RequestMethod.POST)
	@ResponseBody
	public void exportJyy(String userinfo_id, HttpServletResponse response, HttpServletRequest request) {

		String language = LocaleContextHolder.getLocale().getLanguage();
		List<Map<String, Object>> jyyList = new ArrayList<Map<String, Object>>();

		if (StringUtils.isNotBlank(userinfo_id)) {

			String[] ids = userinfo_id.split(",");
			for (String id : ids) {
				UserInfoModel userInfoModel = new UserInfoModel();
				userInfoModel.setUserinfo_id(id);
				Map re = (Map) searchJyyXxById(userInfoModel, request);
				String isSuccess = (String) re.get("isSuccess");
				if ("true".equals(isSuccess)) {
					Map rows = (Map) re.get("rows");
					jyyList.add(rows);
				}
			}

		} else {
			// UserInfoModel infoModel = new UserInfoModel();
			// infoModel.setFlagType(SystemConstants.SYSTEM_SFDM_0);
			// ResponseModel re = searchJyyxxlb(infoModel, request);
			// String isSuccess = re.getIsSuccess();
			// if ("true".equals(isSuccess)) {
			// jyyList = (List<Map<String, Object>>) re.getRows();
			// }

		}

		if (jyyList != null && jyyList.size() > 0) {

			// 读取码表
			String url = env.getProperty("wdcloud.k12.syscode.url") + "/system/syscode/getcodeclass";

			// 性别
			Map<String, Object> xbMap = new HashMap<String, Object>();
			xbMap.put("codeClass", SystemConstants.CODECLASS_XBDM);
			xbMap.put("language", language);
			LinkedHashMap<String, Map<String, Object>> xbCodes_map = appUtil.postForEntity(
					url, xbMap,
					LinkedHashMap.class);

			// 民族
			Map<String, Object> mzMap = new HashMap<String, Object>();
			mzMap.put("codeClass", SystemConstants.CODECLASS_MZDM);
			mzMap.put("language", language);
			LinkedHashMap<String, Map<String, Object>> mzCodes_map = appUtil.postForEntity(
					url, mzMap,
					LinkedHashMap.class);

			// 学段
			Map<String, Object> xdMap = new HashMap<String, Object>();
			xdMap.put("codeClass", SystemConstants.CODECLASS_XDDM);
			xdMap.put("language", language);
			LinkedHashMap<String, Map<String, Object>> xdCodes_map = appUtil.postForEntity(
					url, xdMap,
					LinkedHashMap.class);

			// 学科
			Map<String, Object> xkMap = new HashMap<String, Object>();
			xkMap.put("codeClass", SystemConstants.CODECLASS_ZXXKCDM);
			xkMap.put("language", language);
			LinkedHashMap<String, Map<String, Object>> xkCodes_map = appUtil.postForEntity(
					url, xkMap,
					LinkedHashMap.class);

			for (Map<String, Object> map : jyyList) {
				String gender_code = (String) map.get("gender");
				if (StringUtils.isNotBlank(gender_code)) {
					map.put("gender", xbCodes_map.get(gender_code).get("codedesc"));
				}
				String nation_code = (String) map.get("nation");
				if (StringUtils.isNotBlank(nation_code)) {
					map.put("nation", mzCodes_map.get(nation_code).get("codedesc"));
				}
				if (map.get("birthday") instanceof Long) {
					Long birthday = (Long) map.get("birthday");
					map.put("birthday", new SimpleDateFormat("yyyyMMdd").format(birthday));// yyyy-MM-dd
				}
				if (!map.containsKey("xdkm")) {
					String sectioncode = (String) map.get("sectioncode");
					String subjectcode = (String) map.get("subjectcode");
					String xdkm = "";
					String[] xds = sectioncode.split(",");
					String[] xks = subjectcode.split(",");
					if (xds.length == xks.length) {
						for (int i = 0; i < xks.length; i++) {
							String xd = xds[i];
							String xk = xks[i];
							if (xdCodes_map.containsKey(xd) && xkCodes_map.containsKey(xk)) {
								String xddesc = (String) xdCodes_map.get(xd).get("codedesc");
								String xkdesc = (String) xkCodes_map.get(xk).get("codedesc");
								xdkm += xddesc + xkdesc + "、";
							}
						}
					}
					if (StringUtils.isNotBlank(xdkm)) {
						map.put("xdkm", xdkm.substring(0, xdkm.length() - 1));
					} else {
						map.put("xdkm", "");
					}
				}
			}
		}
		try {
			setExportResponseHeader(response,
					DateUtil.dateToString(new Date(), DateUtil.FMT_DATE_YYYYMMDDHHmmss) + ".xls");
			ServletOutputStream ouputStream = response.getOutputStream();
			// ExcelUtil.export2Excel("template/JYYGL_EXPORT.xls", beans, ouputStream);
			List<ExportDefinition> edList = new ArrayList<>();
			// if ("zh".equals(language)) {
			edList.add(new ExportDefinition("序号", "number", null, null, null));
			edList.add(new ExportDefinition("姓名", "username", null, null, null));
			edList.add(new ExportDefinition("账号", "account", null, null, null));
			edList.add(new ExportDefinition("性别", "gender", null, null, null));
			edList.add(new ExportDefinition("民族", "nation", null, null, null));
			edList.add(new ExportDefinition("出生日期(YYYYMMDD)", "birthday", null, null, null));
			edList.add(new ExportDefinition("学段学科", "xdxk", null, null, null));
			edList.add(new ExportDefinition("组织机构", "org", null, null, null));
			edList.add(new ExportDefinition("手机号", "phone", null, null, null));
			edList.add(new ExportDefinition("邮箱", "email", null, null, null));

			// } else {
			// edList.add(new ExportDefinition("No.", "number", null, null, null));
			// edList.add(new ExportDefinition("Username", "username", null, null, null));
			// edList.add(new ExportDefinition("Account", "account", null, null, null));
			// edList.add(new ExportDefinition("Gender", "gender", null, null, null));
			// edList.add(new ExportDefinition("Nation", "nation", null, null, null));
			// edList.add(new ExportDefinition("Birthday(YYYYMMDD)", "birthday", null, null, null));
			// edList.add(new ExportDefinition("Organization", "org", null, null, null));
			// edList.add(new ExportDefinition("Phone", "phone", null, null, null));
			// edList.add(new ExportDefinition("Email", "email", null, null, null));
			// edList.add(new ExportDefinition("SectionAndSubject", "xdxk", null, null, null));
			// }
			PoiUtil.exportExcel(ouputStream, edList, jyyList);
		} catch (IOException e) {
			log.error("~~~~~~~~~~~~导出异常：" + JSON.toJSONString(jyyList));
		}

	}

	/**
	 * 方法描述: 设置导出响应头</br>
	 * 初始作者: yinzhaomin<br/>
	 * 创建日期: 2017年8月28日-上午11:05:12<br/>
	 * 开始版本: 2.0.0<br/>
	 * =================================================<br/>
	 * 修改记录：<br/>
	 * 修改作者 日期 修改内容<br/>
	 * ================================================<br/>
	 *
	 * @param response
	 * @param fileName
	 * @throws IOException
	 *             void
	 */
	protected void setExportResponseHeader(HttpServletResponse response, String fileName) {

		try {
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ new String(fileName.getBytes("gb2312"), "ISO8859-1") + "\"");// 解决excel文件乱码问题
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setDateHeader("Expires", 0);
		} catch (UnsupportedEncodingException e) {
			log.debug("~~~~~~~~~~~文件名称转换异常：", e);
		}
	}
}
