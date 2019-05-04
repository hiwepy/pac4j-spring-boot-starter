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
package org.pac4j.spring.boot.ext.authentication.generator;

import org.pac4j.core.authorization.generator.AuthorizationGenerator;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.spring.boot.ext.authentication.userdetails.UserDetailsService;

/**
 * TODO
 * @author 		： <a href="https://github.com/vindell">vindell</a>
 */
public class UserDetailsAuthorizationGenerator<U extends CommonProfile> implements AuthorizationGenerator<U> {

	private final UserDetailsService<U> detailsService;
	
	public UserDetailsAuthorizationGenerator(UserDetailsService<U> detailsService) {
		super();
		this.detailsService = detailsService;
	}
	
	@Override
    public U generate(final WebContext context, final U profile) {
		UserDetails details = getDetailsService().loadUserDetails(context, profile);
        profile.addPermissions(details.getPermissions());
        profile.addRoles(details.getRoles());
        return profile;
    }

	public UserDetailsService<U> getDetailsService() {
		return detailsService;
	}
	
}
