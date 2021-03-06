package com.wdcloud.jyx.web.system.model;

import java.io.Serializable;

/**
 * 文件名称： com.wdcloud.jyx.web.system.model.UserInfoModel.java</br>
 * 初始作者： wangpengju</br>
 * 创建日期： 2017年8月8日</br>
 * 功能说明：用户信息实体类 <br/>
 * =================================================<br/>
 * 修改记录：<br/>
 * 修改作者 日期 修改内容<br/>
 * ================================================<br/>
 * Copyright (c) 2010-2011 .All rights reserved.<br/>
 */
public class UserInfoModel implements Serializable {

	/**
	 * 字段描述: 序列号
	 */
	private static final long	serialVersionUID	= -2407238589617187782L;
	/**
	 * 教研员id
	 */
	private String				userinfo_id;
	/**
	 * 姓名
	 */
	private String				username;
	/**
	 * 性别
	 */
	private String				gender;
	/**
	 * 民族
	 */
	private String				nation;
	/**
	 * 出生日期
	 */
	private String				birthday;
	/**
	 * 组织机构
	 */
	private String				orgid;
	/**
	 * 手机号
	 */

	private String				mobilephone;
	/**
	 * 学段
	 */
	private String				sectioncode;
	/**
	 * 科目
	 */
	private String				subjectcode;

	/**
	 * 邮箱
	 */
	private String				email;
	/**
	 * 照片url
	 */
	private String				photoUrl;
	/**
	 * 所在区域
	 */
	private String				location;
	/**
	 * 账号
	 */
	private String				account;
	/**
	 * 每页显示条数
	 */
	private Integer				rows;
	/**
	 * 当前页数
	 */
	private Integer				page;
	/**
	 * 是否创建账号
	 */
	private String				sfcjzh;
	/**
	 * 录入时间
	 */
	private String				lrsj_start;
	/**
	 * 录入时间
	 */
	private String				lrsj_end;
	/**
	 * 当前登录人账号
	 */
	private String				loginid;
	/**
	 * 被创建账号人的信息
	 */
	private String				yxjyy;
	/**
	 * 创建账号的初始密码
	 */
	private String				password;
	/**
	 * 重置的密码
	 */
	private String				new_password;
	/**
	 * 学段
	 */
	private String				xd;
	/**
	 * 是否传orgid
	 */
	private String				sfcorgid;
	/**
	 * 查询标志
	 */
	private String				flagType;

	public String getUserinfo_id() {

		return userinfo_id;
	}

	public void setUserinfo_id(String userinfo_id) {

		this.userinfo_id = userinfo_id;
	}

	public String getUsername() {

		return username;
	}

	public void setUsername(String username) {

		this.username = username;
	}

	public String getGender() {

		return gender;
	}

	public void setGender(String gender) {

		this.gender = gender;
	}

	public String getNation() {

		return nation;
	}

	public void setNation(String nation) {

		this.nation = nation;
	}

	public String getBirthday() {

		return birthday;
	}

	public void setBirthday(String birthday) {

		this.birthday = birthday;
	}

	public String getOrgid() {

		return orgid;
	}

	public void setOrgid(String orgid) {

		this.orgid = orgid;
	}

	public String getMobilephone() {

		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {

		this.mobilephone = mobilephone;
	}

	public String getSectioncode() {

		return sectioncode;
	}

	public void setSectioncode(String sectioncode) {

		this.sectioncode = sectioncode;
	}

	public String getSubjectcode() {

		return subjectcode;
	}

	public void setSubjectcode(String subjectcode) {

		this.subjectcode = subjectcode;
	}

	public String getEmail() {

		return email;
	}

	public void setEmail(String email) {

		this.email = email;
	}

	public String getPhotoUrl() {

		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {

		this.photoUrl = photoUrl;
	}

	public String getLocation() {

		return location;
	}

	public void setLocation(String location) {

		this.location = location;
	}

	public String getAccount() {

		return account;
	}

	public void setAccount(String account) {

		this.account = account;
	}

	public Integer getRows() {

		return rows;
	}

	public void setRows(Integer rows) {

		this.rows = rows;
	}

	public Integer getPage() {

		return page;
	}

	public void setPage(Integer page) {

		this.page = page;
	}

	public String getSfcjzh() {

		return sfcjzh;
	}

	public void setSfcjzh(String sfcjzh) {

		this.sfcjzh = sfcjzh;
	}

	public String getLrsj_start() {

		return lrsj_start;
	}

	public void setLrsj_start(String lrsj_start) {

		this.lrsj_start = lrsj_start;
	}

	public String getLrsj_end() {

		return lrsj_end;
	}

	public void setLrsj_end(String lrsj_end) {

		this.lrsj_end = lrsj_end;
	}

	public String getLoginid() {

		return loginid;
	}

	public void setLoginid(String loginid) {

		this.loginid = loginid;
	}

	public String getYxjyy() {

		return yxjyy;
	}

	public void setYxjyy(String yxjyy) {

		this.yxjyy = yxjyy;
	}

	public String getPassword() {

		return password;
	}

	public void setPassword(String password) {

		this.password = password;
	}

	public String getNew_password() {

		return new_password;
	}

	public void setNew_password(String new_password) {

		this.new_password = new_password;
	}

	public String getXd() {

		return xd;
	}

	public void setXd(String xd) {

		this.xd = xd;
	}

	public String getSfcorgid() {

		return sfcorgid;
	}

	public void setSfcorgid(String sfcorgid) {

		this.sfcorgid = sfcorgid;
	}

	public String getFlagType() {

		return flagType;
	}

	public void setFlagType(String flagType) {

		this.flagType = flagType;
	}

	@Override
	public String toString() {

		return "UserInfoModel [userinfo_id=" + userinfo_id + ", username=" + username + ", gender=" + gender
				+ ", nation=" + nation + ", birthday=" + birthday + ", orgid=" + orgid + ", mobilephone=" + mobilephone
				+ ", sectioncode=" + sectioncode + ", subjectcode=" + subjectcode + ", email=" + email + ", photoUrl="
				+ photoUrl + ", location=" + location + ", account=" + account + ", rows=" + rows + ", page=" + page
				+ ", sfcjzh=" + sfcjzh + ", lrsj_start=" + lrsj_start + ", lrsj_end=" + lrsj_end + ", loginid="
				+ loginid + ", yxjyy=" + yxjyy + ", password=" + password + ", new_password=" + new_password + ", xd="
				+ xd + ", sfcorgid=" + sfcorgid + ", flagType=" + flagType + "]";
	}

}
