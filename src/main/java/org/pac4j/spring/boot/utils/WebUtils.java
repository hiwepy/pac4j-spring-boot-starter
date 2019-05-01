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
package org.pac4j.spring.boot.utils;

import org.pac4j.core.context.HttpConstants;
import org.pac4j.core.context.WebContext;
import org.springframework.http.HttpMethod;

/**
 * TODO
 * @author ： <a href="https://github.com/vindell">vindell</a>
 */
public class WebUtils {

	public static boolean isAjaxRequest(WebContext context) {
		return context.getRequestHeader(HttpConstants.AJAX_HEADER_NAME).contains(HttpConstants.AJAX_HEADER_VALUE);
	}

	public static boolean isContentTypeJson(WebContext context) {
		return context.getRequestHeader(HttpConstants.CONTENT_TYPE_HEADER).contains(HttpConstants.APPLICATION_JSON);
	}

	public static boolean isPostRequest(WebContext context) {
		return HttpMethod.POST.name().equals(context.getRequestMethod());
	}

}
