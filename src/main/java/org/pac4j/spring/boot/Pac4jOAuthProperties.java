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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ConfigurationProperties(Pac4jOAuthProperties.PREFIX)
@Getter
@Setter
@ToString
public class Pac4jOAuthProperties {

	public static final String PREFIX = "pac4j.oauth";
	
	/** Whether Enable Pac4j OAuth. */
	private boolean enabled = false;
	
	@NestedConfigurationProperty
	private Pac4jOAuthClientProperties baidu = new Pac4jOAuthClientProperties();
	@NestedConfigurationProperty
	private Pac4jOAuthClientProperties bitbucket = new Pac4jOAuthClientProperties();
	@NestedConfigurationProperty
	private Pac4jOAuthCasClientProperties cas = new Pac4jOAuthCasClientProperties();
	@NestedConfigurationProperty
	private Pac4jOAuthClientProperties dropbox = new Pac4jOAuthClientProperties();
	@NestedConfigurationProperty
	private Pac4jOAuthFacebookClientProperties facebook = new Pac4jOAuthFacebookClientProperties();
	@NestedConfigurationProperty
	private Pac4jOAuthClientProperties foursquare = new Pac4jOAuthClientProperties();
	@NestedConfigurationProperty
	private Pac4jOAuthClientProperties github = new Pac4jOAuthClientProperties();
	@NestedConfigurationProperty
	private Pac4jOAuthClientProperties google2 = new Pac4jOAuthClientProperties();
	@NestedConfigurationProperty
	private Pac4jOAuthClientProperties linkedin2 = new Pac4jOAuthClientProperties();
	@NestedConfigurationProperty
	private Pac4jOAuthOkClientProperties ok = new Pac4jOAuthOkClientProperties();
	@NestedConfigurationProperty
	private Pac4jOAuthClientProperties orcid = new Pac4jOAuthClientProperties();
	@NestedConfigurationProperty
	private Pac4jOAuthClientProperties oschina = new Pac4jOAuthClientProperties();
	@NestedConfigurationProperty
	private Pac4jOAuthClientProperties paypal = new Pac4jOAuthClientProperties();
	@NestedConfigurationProperty
	private Pac4jOAuthClientProperties qq = new Pac4jOAuthClientProperties();
	@NestedConfigurationProperty
	private Pac4jOAuthClientProperties renren = new Pac4jOAuthClientProperties();
	@NestedConfigurationProperty
	private Pac4jOAuthStravaClientProperties strava = new Pac4jOAuthStravaClientProperties();
	@NestedConfigurationProperty
	private Pac4jOAuthClientProperties sohu = new Pac4jOAuthClientProperties();
	@NestedConfigurationProperty
	private Pac4jOAuthClientProperties twitter = new Pac4jOAuthClientProperties();
	@NestedConfigurationProperty
	private Pac4jOAuthClientProperties vk = new Pac4jOAuthClientProperties();
	@NestedConfigurationProperty
	private Pac4jOAuthClientProperties weibo = new Pac4jOAuthClientProperties();
	@NestedConfigurationProperty
	private Pac4jOAuthClientProperties weixin = new Pac4jOAuthClientProperties();
	@NestedConfigurationProperty
	private Pac4jOAuthClientProperties windowslive = new Pac4jOAuthClientProperties();
	@NestedConfigurationProperty
	private Pac4jOAuthClientProperties wordpress = new Pac4jOAuthClientProperties();
	@NestedConfigurationProperty
	private Pac4jOAuthClientProperties yahoo = new Pac4jOAuthClientProperties();
	@NestedConfigurationProperty
	private Pac4jOAuthClientProperties yiban = new Pac4jOAuthClientProperties();

}
