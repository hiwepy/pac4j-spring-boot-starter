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
package org.pac4j.spring.boot.ext.client;

import static org.pac4j.core.util.CommonHelper.toNiceString;

import java.util.Optional;

import org.pac4j.cas.client.CasProxyReceptor;
import org.pac4j.core.client.IndirectClient;
import org.pac4j.core.credentials.TokenCredentials;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.exception.http.OkAction;
import org.pac4j.core.exception.http.RedirectionActionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 * @author 		： <a href="https://github.com/vindell">wandl</a>
 */

public class FrontendProxyReceptor extends IndirectClient<TokenCredentials> {

    public static final String PARAM_PROXY_TARGET = "target";

    private static final Logger logger = LoggerFactory.getLogger(CasProxyReceptor.class);
    
    @Override
    protected void clientInit() {

        defaultCredentialsExtractor(ctx -> {
        	
            final Optional<String> proxyTarget = ctx.getRequestParameter(PARAM_PROXY_TARGET);
            logger.debug("proxyTarget: {}", proxyTarget);

            if (!proxyTarget.isPresent()) {
                logger.warn("Missing proxyGrantingTicket or proxyGrantingTicketIou -> returns ok");
                throw new OkAction("");
            }

            throw new OkAction(proxyTarget.get());
        });
        
        defaultRedirectionActionBuilder(ctx -> {
        	return Optional.of(RedirectionActionHelper.buildRedirectUrlAction(ctx, callbackUrl));
        });
        
        defaultAuthenticator((credentials, ctx) -> { throw new TechnicalException("Not supported by the CAS proxy receptor"); });
    }

    @Override
    public String toString() {
        return toNiceString(this.getClass(), "callbackUrl", this.callbackUrl);
    }

}
