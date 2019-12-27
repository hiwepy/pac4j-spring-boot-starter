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

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.pac4j.core.ext.Pac4jExtConstants;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Pac4TokenProperties {
	
    private String clientName = "token";
	/**
	 * Defines the location of the uniauth server token validate URL. i.e. https://localhost:8080/uniauth/ser/vaildTocken.action
	 */
	private String profileUrl;
	private String tokenParamName = Pac4jExtConstants.TOKEN;
	private boolean supportGetRequest = true;
	private boolean supportPostRequest = true;
	/* Map containing user defined headers */
	private Map<String, String> customHeaders = new HashMap<>();
	/* Map containing user defined parameters */
	private Map<String, String> customParams = new HashMap<>();

	private boolean encodeParams = true;

	private String charset = StandardCharsets.UTF_8.name();

    protected String defaultUrl;

}
