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
package org.pac4j.spring.boot.ext.redirect;

import java.util.Optional;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.exception.http.RedirectionAction;
import org.pac4j.core.exception.http.RedirectionActionHelper;
import org.pac4j.core.http.callback.CallbackUrlResolver;
import org.pac4j.core.redirect.RedirectionActionBuilder;
import org.pac4j.core.util.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 * @author 		： <a href="https://github.com/vindell">wandl</a>
 */
public class FrontendRedirectionActionBuilder implements RedirectionActionBuilder {

    private static final Logger logger = LoggerFactory.getLogger(FrontendRedirectionActionBuilder.class);
    
    /**
  	 * The location of the front-end server login URL, 
  	 * i.e. 
  	 * http://localhost:8080/#/client?target=/portal
  	 * http://localhost:8080/#/client?client_name=cas&target=/portal
  	 */
    protected String callbackUrl;

    protected CallbackUrlResolver callbackUrlResolver;

    public FrontendRedirectionActionBuilder() {
    }
    
    @Override
    public Optional<RedirectionAction> redirect(final WebContext context) {
    	
    	CommonHelper.assertNotNull("callbackUrl", callbackUrl);
    	
        final String redirectionUrl = CommonHelper.addParameter(callbackUrl, "method", "");
        logger.debug("redirectionUrl: {}", redirectionUrl);
        return Optional.of(RedirectionActionHelper.buildRedirectUrlAction(context, redirectionUrl));
    }

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public CallbackUrlResolver getCallbackUrlResolver() {
		return callbackUrlResolver;
	}

	public void setCallbackUrlResolver(CallbackUrlResolver callbackUrlResolver) {
		this.callbackUrlResolver = callbackUrlResolver;
	}
	
}
