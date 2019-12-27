/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
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
package org.pac4j.spring.boot;

/**
 * TODO
 * @author 		： <a href="https://github.com/hiwepy">wandl</a>
 */
public interface Pac4jClientNames {

	public static String CAS_CLIENT = "cas-client";
	public static String DIRECT_CAS_CLIENT = "direct-cas-client";
	public static String DIRECT_CAS_PROXY_CLIENT = "direct-cas-proxy-client";
	public static String CAS_REST_BASIC_AUTH_CLIENT = "cas-rest-basic-auth-client";
	public static String CAS_REST_FORM_CLIENT = "cas-rest-form-client";
	
	public static String FORM_CLIENT = "form-client";
	public static String INDIRECT_BASIC_AUTH_CLIENT = "indirect-basic-auth-client";
	public static String DIRECT_BASIC_AUTH_CLIENT = "direct-basic-auth-client";
	
	

}
