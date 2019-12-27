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

import org.pac4j.core.http.ajax.AjaxRequestResolver;
import org.pac4j.core.http.url.UrlResolver;
import org.pac4j.core.state.StateGenerator;
import org.pac4j.core.state.StaticOrRandomStateGenerator;
import org.pac4j.oauth.client.BaiduClient;
import org.pac4j.oauth.client.BitbucketClient;
import org.pac4j.oauth.client.CasOAuthWrapperClient;
import org.pac4j.oauth.client.DropBoxClient;
import org.pac4j.oauth.client.FacebookClient;
import org.pac4j.oauth.client.FoursquareClient;
import org.pac4j.oauth.client.GitHubClient;
import org.pac4j.oauth.client.Google2Client;
import org.pac4j.oauth.client.LinkedIn2Client;
import org.pac4j.oauth.client.OAuth10Client;
import org.pac4j.oauth.client.OAuth20Client;
import org.pac4j.oauth.client.OkClient;
import org.pac4j.oauth.client.OrcidClient;
import org.pac4j.oauth.client.PayPalClient;
import org.pac4j.oauth.client.QQClient;
import org.pac4j.oauth.client.StravaClient;
import org.pac4j.oauth.client.TwitterClient;
import org.pac4j.oauth.client.VkClient;
import org.pac4j.oauth.client.WechatClient;
import org.pac4j.oauth.client.WeiboClient;
import org.pac4j.oauth.client.WindowsLiveClient;
import org.pac4j.oauth.client.WordPressClient;
import org.pac4j.oauth.client.YahooClient;
import org.pac4j.oauth.client.YibanClient;
import org.pac4j.oauth.config.OAuth10Configuration;
import org.pac4j.oauth.config.OAuth20Configuration;
import org.pac4j.oauth.profile.OAuth10Profile;
import org.pac4j.oauth.profile.OAuth20Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.scribejava.apis.SinaWeiboApi20;
import com.github.scribejava.core.builder.api.DefaultApi20;

@Configuration
@AutoConfigureBefore(Pac4jAutoConfiguration.class)
@ConditionalOnClass({ OAuth20Client.class, DefaultApi20.class, SinaWeiboApi20.class })
@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ Pac4jOAuthProperties.class, Pac4jProperties.class, ServerProperties.class })
public class Pac4jOAuthConfiguration {

	@Autowired
	private Pac4jOAuthProperties oauthProperties; 
	@Autowired
	private Pac4jProperties pac4jProperties;

	@Bean
	@ConditionalOnMissingBean
	protected StateGenerator stateGenerator() {
		StateGenerator stateGenerator = new StaticOrRandomStateGenerator();
		return stateGenerator;
	}
	 
	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "baidu")
	public BaiduClient baiduClient(AjaxRequestResolver ajaxRequestResolver, UrlResolver urlResolver) {
		
		final Pac4jOAuthClientProperties properties = oauthProperties.getBaidu();
		final BaiduClient client = new BaiduClient(properties.getKey(), properties.getSecret());
		this.initOAuth20Client(client, properties, ajaxRequestResolver, urlResolver);
		
		return client;
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "bitbucket")
	public BitbucketClient bitbucketClient(AjaxRequestResolver ajaxRequestResolver, UrlResolver urlResolver) {
		
		final Pac4jOAuthClientProperties properties = oauthProperties.getBitbucket();
		final BitbucketClient client = new BitbucketClient(properties.getKey(), properties.getSecret());
		this.initOAuth10Client(client, properties, ajaxRequestResolver, urlResolver);
		
		return client;
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "cas")
	public CasOAuthWrapperClient casOAuthWrapperClient(AjaxRequestResolver ajaxRequestResolver, UrlResolver urlResolver) {
		
		final Pac4jOAuthCasClientProperties properties = oauthProperties.getCas();
		final CasOAuthWrapperClient client = new CasOAuthWrapperClient(properties.getKey(), properties.getSecret(), properties.getCasOAuthUrl());
		this.initOAuth20Client(client, properties, ajaxRequestResolver, urlResolver);
		
		return client;
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "dropbox")
	public DropBoxClient dropboxClient(AjaxRequestResolver ajaxRequestResolver, UrlResolver urlResolver) {
		
		final Pac4jOAuthClientProperties properties = oauthProperties.getDropbox();
		final DropBoxClient client = new DropBoxClient(properties.getKey(), properties.getSecret());
		this.initOAuth20Client(client, properties, ajaxRequestResolver, urlResolver);
		
		return client;
	}

	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "facebook")
	public FacebookClient facebookClient(AjaxRequestResolver ajaxRequestResolver, UrlResolver urlResolver) {

		final Pac4jOAuthFacebookClientProperties properties = oauthProperties.getFacebook();
		final FacebookClient client = new FacebookClient(properties.getKey(), properties.getSecret());
		this.initOAuth20Client(client, properties, ajaxRequestResolver, urlResolver);
		
		client.setFields(properties.getFields());
		//client.setRequiresExtendedToken(properties.isRequiresExtendedToken());
		client.setLimit(properties.getLimit());
		
		return client;
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "foursquare")
	public FoursquareClient foursquareClient(AjaxRequestResolver ajaxRequestResolver, UrlResolver urlResolver) {
		
		final Pac4jOAuthClientProperties properties = oauthProperties.getFoursquare();
		final FoursquareClient client = new FoursquareClient(properties.getKey(), properties.getSecret());
		this.initOAuth20Client(client, properties, ajaxRequestResolver, urlResolver);
		
		return client;
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "github")
	public GitHubClient githubClient(AjaxRequestResolver ajaxRequestResolver, UrlResolver urlResolver) {
		
		final Pac4jOAuthClientProperties properties = oauthProperties.getGithub();
		final GitHubClient client = new GitHubClient(properties.getKey(), properties.getSecret());
		this.initOAuth20Client(client, properties, ajaxRequestResolver, urlResolver);
		
		return client;
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "google2")
	public Google2Client google2Client(AjaxRequestResolver ajaxRequestResolver, UrlResolver urlResolver) {
		
		final Pac4jOAuthClientProperties properties = oauthProperties.getGoogle2();
		final Google2Client client = new Google2Client(properties.getKey(), properties.getSecret());
		this.initOAuth20Client(client, properties, ajaxRequestResolver, urlResolver);
		
		return client;
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "linkedin2")
	public LinkedIn2Client linkedin2Client(AjaxRequestResolver ajaxRequestResolver, UrlResolver urlResolver) {
		
		final Pac4jOAuthClientProperties properties = oauthProperties.getLinkedin2();
		final LinkedIn2Client client = new LinkedIn2Client(properties.getKey(), properties.getSecret());
		this.initOAuth20Client(client, properties, ajaxRequestResolver, urlResolver);
		
		return client;
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "ok")
	public OkClient okClient(AjaxRequestResolver ajaxRequestResolver, UrlResolver urlResolver) {
		
		final Pac4jOAuthOkClientProperties properties = oauthProperties.getOk();
		final OkClient client = new OkClient(properties.getKey(), properties.getSecret(), properties.getPublicKey());
		this.initOAuth20Client(client, properties, ajaxRequestResolver, urlResolver);
		
		return client;
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "orcid")
	public OrcidClient orcidClient(AjaxRequestResolver ajaxRequestResolver, UrlResolver urlResolver) {
		
		final Pac4jOAuthClientProperties properties = oauthProperties.getOrcid();
		final OrcidClient client = new OrcidClient(properties.getKey(), properties.getSecret());
		this.initOAuth20Client(client, properties, ajaxRequestResolver, urlResolver);
		
		return client;
	}
	
	/*@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "oschina")
	public OschinaClient oschinaClient(AjaxRequestResolver ajaxRequestResolver, UrlResolver urlResolver) {
		
		final Pac4jOAuthClientProperties properties = oauthProperties.getOschina();
		final OschinaClient client = new OschinaClient(properties.getKey(), properties.getSecret());
		this.initOAuth20Client(client, properties, ajaxRequestResolver, urlResolver);
		
		return client;
	}*/
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "paypal")
	public PayPalClient paypalClient(AjaxRequestResolver ajaxRequestResolver, UrlResolver urlResolver) {
		
		final Pac4jOAuthClientProperties properties = oauthProperties.getPaypal();
		final PayPalClient client = new PayPalClient(properties.getKey(), properties.getSecret());
		this.initOAuth20Client(client, properties, ajaxRequestResolver, urlResolver);
		
		return client;
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "qq")
	public QQClient qqClient(AjaxRequestResolver ajaxRequestResolver, UrlResolver urlResolver) {
		
		final Pac4jOAuthClientProperties properties = oauthProperties.getQq();
		final QQClient client = new QQClient(properties.getKey(), properties.getSecret());
		this.initOAuth20Client(client, properties, ajaxRequestResolver, urlResolver);
		
		return client;
	}
	
	/*@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "renren")
	public BaiduClient renrenClient(AjaxRequestResolver ajaxRequestResolver, UrlResolver urlResolver) {
		
		final Pac4jOAuthClientProperties properties = oauthProperties.getRenren();
		final BaiduClient client = new BaiduClient(properties.getKey(), properties.getSecret());
		this.initOAuth20Client(client, properties, ajaxRequestResolver, urlResolver);
		
		return client;
	}*/
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "strava")
	public StravaClient stravaClient(AjaxRequestResolver ajaxRequestResolver, UrlResolver urlResolver) {
		
		final Pac4jOAuthStravaClientProperties properties = oauthProperties.getStrava();
		final StravaClient client = new StravaClient(properties.getKey(), properties.getSecret());
		this.initOAuth20Client(client, properties, ajaxRequestResolver, urlResolver);
		client.setApprovalPrompt(properties.getApprovalPrompt());
		
		return client;
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "twitter")
	public TwitterClient twitterClient(AjaxRequestResolver ajaxRequestResolver, UrlResolver urlResolver) {
		
		final Pac4jOAuthClientProperties properties = oauthProperties.getTwitter();
		final TwitterClient client = new TwitterClient(properties.getKey(), properties.getSecret());
		this.initOAuth10Client(client, properties, ajaxRequestResolver, urlResolver);
		
		return client;
		
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "vk")
	public VkClient vkClient(AjaxRequestResolver ajaxRequestResolver, UrlResolver urlResolver) {
		
		final Pac4jOAuthClientProperties properties = oauthProperties.getVk();
		final VkClient client = new VkClient(properties.getKey(), properties.getSecret());
		this.initOAuth20Client(client, properties, ajaxRequestResolver, urlResolver);
		
		return client;
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "weibo")
	public WeiboClient weiboClient(AjaxRequestResolver ajaxRequestResolver, UrlResolver urlResolver) {
		
		final Pac4jOAuthClientProperties properties = oauthProperties.getWeibo();
		final WeiboClient client = new WeiboClient(properties.getKey(), properties.getSecret());
		this.initOAuth20Client(client, properties, ajaxRequestResolver, urlResolver);
		
		return client;
		
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "wechat")
	public WechatClient wechatClient(AjaxRequestResolver ajaxRequestResolver, UrlResolver urlResolver) {
		
		final Pac4jOAuthClientProperties properties = oauthProperties.getWeixin();
		final WechatClient client = new WechatClient(properties.getKey(), properties.getSecret());
		this.initOAuth20Client(client, properties, ajaxRequestResolver, urlResolver);
		
		return client;
		
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "windowslive")
	public WindowsLiveClient windowsliveClient(AjaxRequestResolver ajaxRequestResolver, UrlResolver urlResolver) {
		
		final Pac4jOAuthClientProperties properties = oauthProperties.getWindowslive();
		final WindowsLiveClient client = new WindowsLiveClient(properties.getKey(), properties.getSecret());
		this.initOAuth20Client(client, properties, ajaxRequestResolver, urlResolver);
		
		return client;
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "wordpress")
	public WordPressClient wordpressClient(AjaxRequestResolver ajaxRequestResolver, UrlResolver urlResolver) {
		
		final Pac4jOAuthClientProperties properties = oauthProperties.getWordpress();
		final WordPressClient client = new WordPressClient(properties.getKey(), properties.getSecret());
		this.initOAuth20Client(client, properties, ajaxRequestResolver, urlResolver);
		
		return client;
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "yahoo")
	public YahooClient yahooClient(AjaxRequestResolver ajaxRequestResolver, UrlResolver urlResolver) {
		
		final Pac4jOAuthClientProperties properties = oauthProperties.getYahoo();
		final YahooClient client = new YahooClient(properties.getKey(), properties.getSecret());
		this.initOAuth10Client(client, properties, ajaxRequestResolver, urlResolver);
		
		return client;
	}
	
	@Bean
	@ConditionalOnProperty(prefix = Pac4jOAuthProperties.PREFIX, value = "yiban")
	public YibanClient yibanClient(AjaxRequestResolver ajaxRequestResolver, UrlResolver urlResolver) {
		
		final Pac4jOAuthClientProperties properties = oauthProperties.getGithub();
		final YibanClient client = new YibanClient(properties.getKey(), properties.getSecret());
		this.initOAuth20Client(client, properties, ajaxRequestResolver, urlResolver);
		
		return client;
	}
	
	
	protected <U extends OAuth10Profile> void initOAuth10Client(OAuth10Client client,
			Pac4jOAuthClientProperties properties, AjaxRequestResolver ajaxRequestResolver,
			UrlResolver urlResolver) {

		final OAuth10Configuration configuration = client.getConfiguration();
		
		//configuration.setConnectTimeout(properties.getConnectTimeout());
		//configuration.setHasGrantType(properties.isHasGrantType());
		//configuration.setReadTimeout(properties.getReadTimeout());
		configuration.setResponseType(properties.getResponseType());
		configuration.setTokenAsHeader(properties.isTokenAsHeader());
		
		client.setName(properties.getName());
		client.setKey(properties.getKey());
		client.setConfiguration(configuration);
		//client.setIncludeClientNameInCallbackUrl(pac4jProperties.isIncludeClientNameInCallbackUrl());
		client.setSecret(properties.getSecret());
		client.setUrlResolver(urlResolver);
		
	}

	protected <U extends OAuth20Profile> void initOAuth20Client(OAuth20Client client,
			Pac4jOAuthClientProperties properties, AjaxRequestResolver ajaxRequestResolver,
			UrlResolver urlResolver) {

		final OAuth20Configuration configuration = client.getConfiguration();
		
		configuration.setCustomParams(properties.getCustomParams());
		//configuration.setHasGrantType(properties.isHasGrantType());
		//configuration.setReadTimeout(properties.getReadTimeout());
		configuration.setScope(properties.getScope());
		configuration.setResponseType(properties.getResponseType());
		configuration.setWithState(properties.isWithState());
		//configuration.setStateData(properties.getStateData());
		configuration.setTokenAsHeader(properties.isTokenAsHeader());
		
		client.setName(properties.getName());
		client.setAjaxRequestResolver(ajaxRequestResolver);
		client.setCallbackUrl(properties.getCallbackUrl());
		client.setKey(properties.getKey());
		client.setConfiguration(configuration);
		//client.setIncludeClientNameInCallbackUrl(pac4jProperties.isIncludeClientNameInCallbackUrl());
		client.setSecret(properties.getSecret());
		client.setUrlResolver(urlResolver);
	}
	
	
}
