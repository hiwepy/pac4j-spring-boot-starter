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

import java.util.HashMap;
import java.util.Map;

import org.pac4j.core.context.HttpConstants;

import com.github.scribejava.core.model.Verb;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Pac4jOAuthClientProperties {

	public static final String RESPONSE_TYPE_CODE = "code";

    /** Defines the location of the client callback URL, i.e. https://localhost:8080/myapp/callback */
	private String callbackUrl;
	
	private String name;
	private String desc;
	private String logoUrl;
	private String key;
	private String secret;
	private boolean tokenAsHeader;
	private String responseType = RESPONSE_TYPE_CODE;
	private String scope;
	private boolean hasGrantType;
	private int connectTimeout = HttpConstants.DEFAULT_CONNECT_TIMEOUT;
	private int readTimeout = HttpConstants.DEFAULT_READ_TIMEOUT;
	private Verb profileVerb = Verb.POST;

	/* Map containing user defined parameters */
	private Map<String, String> customParams = new HashMap<String, String>();
	private Map<String, String> profileAttrs = new HashMap<String, String>();

	private boolean withState;

	private String stateData;

}
