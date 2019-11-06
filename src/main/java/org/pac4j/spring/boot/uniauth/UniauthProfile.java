/*
 * Copyright (c) 2018, vindell (https://github.com/vindell).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.pac4j.spring.boot.uniauth;

import org.pac4j.core.ext.profile.TokenProfile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UniauthProfile extends TokenProfile {

	/**
	 * 用户 id
	 */
	private String userid;
	
	/**
	 * 统一的人员编号
	 */
	private String pid;
	
	/**
	 * 姓名
	 */
	private String xm;
	
	/**
	 * 人员类型：学生／教工
	 */
	private String ptype;
	
	/**
	 * 出生日期
	 */
	private String csrq;
	
	/**
	 * 0 未初始化密码 1 已初始化密码
	 */
	private String flag;
	
}
