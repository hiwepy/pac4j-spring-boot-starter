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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pac4jUrlUtils {

	 private static final Logger LOGGER = LoggerFactory.getLogger(Pac4jUrlUtils.class);

	 /**
     * Sends the redirect message and captures the exceptions that we can't possibly do anything with.
     *
     * @param response the HttpServletResponse.  CANNOT be NULL.
     * @param url the url to redirect to.
     */
    public static void sendRedirect(final HttpServletResponse response, final String url) {
        try {
            response.sendRedirect(url);
        } catch (final IOException e) {
            LOGGER.warn(e.getMessage(), e);
        }

    }

	public static String constructRedirectUrl(String serviceUrl, String clientParameterName, String clientName) {
		return serviceUrl + (serviceUrl.contains("?") ? "&" : "?") + clientParameterName + "=" + clientName;
	}
	
    public static String constructRedirectUrl(final String serviceUrl, final String clientParameterName,
            final String clientName, final boolean encodeUrl) {
        return (encodeUrl ? urlEncode(serviceUrl) : serviceUrl) + (serviceUrl.contains("?") ? "&" : "?") + clientParameterName + "=" + clientName;
    }

    /**
     * Url encode a value using UTF-8 encoding.
     * 
     * @param value the value to encode.
     * @return the encoded value.
     */
    public static String urlEncode(final String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

	
}
