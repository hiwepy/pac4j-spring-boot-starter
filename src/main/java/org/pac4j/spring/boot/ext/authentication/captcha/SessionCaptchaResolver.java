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
package org.pac4j.spring.boot.ext.authentication.captcha;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.pac4j.core.context.WebContext;

@SuppressWarnings("unchecked")
public class SessionCaptchaResolver implements CaptchaResolver {

	/**
	 * Name of the session attribute that holds the Kaptcha name. Only used
	 * internally by this implementation.
	 */
	public static final String KAPTCHA_SESSION_ATTRIBUTE_NAME = SessionCaptchaResolver.class.getName() + ".KAPTCHA";
	public static final String KAPTCHA_DATE_SESSION_ATTRIBUTE_NAME = SessionCaptchaResolver.class.getName() + ".KAPTCHA_DATE";

	private String sessionKeyValue = KAPTCHA_SESSION_ATTRIBUTE_NAME;
	private String sessionKeyDateValue = KAPTCHA_DATE_SESSION_ATTRIBUTE_NAME;
	
	@Override
	public boolean validCaptcha(WebContext context, String capText) {
		if(StringUtils.isEmpty(capText)){
			return false;
		}
		
		String sessionCapText = (String) context.getSessionStore().get(context, this.sessionKeyValue);
		//String sessionCapDate = (String) WebUtils.getSessionAttribute(request, this.sessionKeyDateValue);
		if (sessionCapText != null) {
			return StringUtils.equalsIgnoreCase(sessionCapText, capText);
		}
		return false;
	}

	@Override
	public void setCaptcha(WebContext context, String capText, Date capDate) {
		
		// store the text in the session
		context.getSessionStore().set(context, sessionKeyValue, (StringUtils.isNotEmpty(capText) ? capText : null));

		// store the date in the session so that it can be compared
		// against to make sure someone hasn't taken too long to enter
		// their kaptcha
		context.getSessionStore().set(context, sessionKeyDateValue, (capDate != null ? capDate : new Date()) );

	}


}
