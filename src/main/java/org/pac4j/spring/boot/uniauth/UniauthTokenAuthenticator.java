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

import org.pac4j.core.credentials.TokenCredentials;
import org.pac4j.core.ext.credentials.authenticator.TokenAuthenticator;
import org.pac4j.core.util.CommonHelper;

/**
 * TODO
 * @author 		： <a href="https://github.com/vindell">vindell</a>
 */
public class UniauthTokenAuthenticator extends TokenAuthenticator<TokenCredentials, UniauthProfile, UniauthToken> {
	
	private String profileUrl;
		
	public UniauthTokenAuthenticator(String profileUrl) {
		this.profileUrl = profileUrl;
	}
	
	@Override
    protected void internalInit() {
		CommonHelper.assertNotNull("profileUrl", profileUrl);
        defaultProfileDefinition(new UniauthProfileDefinition(profileUrl, x -> new UniauthProfile()));
        super.internalInit();
    }
    
	public String getProfileUrl() {
		return profileUrl;
	}

	@Override
	protected UniauthToken getAccessToken(TokenCredentials credentials) {
		return new UniauthToken(credentials.getToken());
	}    
    
}
