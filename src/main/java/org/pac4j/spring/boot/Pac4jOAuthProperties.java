/*
 * Copyright (c) 2017, vindell (https://github.com/vindell).
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

import java.util.ArrayList;
import java.util.List;

import org.pac4j.spring.boot.ext.property.Pac4jOAuthCasClientProperties;
import org.pac4j.spring.boot.ext.property.Pac4jOAuthClientProperties;
import org.pac4j.spring.boot.ext.property.Pac4jOAuthFacebookClientProperties;
import org.pac4j.spring.boot.ext.property.Pac4jOAuthGenericProperties;
import org.pac4j.spring.boot.ext.property.Pac4jOAuthOkClientProperties;
import org.pac4j.spring.boot.ext.property.Pac4jOAuthStravaClientProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(Pac4jOAuthProperties.PREFIX)
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
	private List<Pac4jOAuthGenericProperties> generics = new ArrayList<Pac4jOAuthGenericProperties>();
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
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Pac4jOAuthClientProperties getBaidu() {
		return baidu;
	}

	public void setBaidu(Pac4jOAuthClientProperties baidu) {
		this.baidu = baidu;
	}

	public Pac4jOAuthClientProperties getBitbucket() {
		return bitbucket;
	}

	public void setBitbucket(Pac4jOAuthClientProperties bitbucket) {
		this.bitbucket = bitbucket;
	}

	public Pac4jOAuthCasClientProperties getCas() {
		return cas;
	}

	public void setCas(Pac4jOAuthCasClientProperties cas) {
		this.cas = cas;
	}

	public Pac4jOAuthClientProperties getDropbox() {
		return dropbox;
	}

	public void setDropbox(Pac4jOAuthClientProperties dropbox) {
		this.dropbox = dropbox;
	}

	public Pac4jOAuthFacebookClientProperties getFacebook() {
		return facebook;
	}

	public void setFacebook(Pac4jOAuthFacebookClientProperties facebook) {
		this.facebook = facebook;
	}

	public Pac4jOAuthClientProperties getFoursquare() {
		return foursquare;
	}

	public void setFoursquare(Pac4jOAuthClientProperties foursquare) {
		this.foursquare = foursquare;
	}

	public List<Pac4jOAuthGenericProperties> getGenerics() {
		return generics;
	}

	public void setGenerics(List<Pac4jOAuthGenericProperties> generics) {
		this.generics = generics;
	}

	public Pac4jOAuthClientProperties getGithub() {
		return github;
	}

	public void setGithub(Pac4jOAuthClientProperties github) {
		this.github = github;
	}

	public Pac4jOAuthClientProperties getGoogle2() {
		return google2;
	}

	public void setGoogle2(Pac4jOAuthClientProperties google2) {
		this.google2 = google2;
	}

	public Pac4jOAuthClientProperties getLinkedin2() {
		return linkedin2;
	}

	public void setLinkedin2(Pac4jOAuthClientProperties linkedin2) {
		this.linkedin2 = linkedin2;
	}

	public Pac4jOAuthOkClientProperties getOk() {
		return ok;
	}

	public void setOk(Pac4jOAuthOkClientProperties ok) {
		this.ok = ok;
	}

	public Pac4jOAuthClientProperties getOrcid() {
		return orcid;
	}

	public void setOrcid(Pac4jOAuthClientProperties orcid) {
		this.orcid = orcid;
	}

	public Pac4jOAuthClientProperties getOschina() {
		return oschina;
	}

	public void setOschina(Pac4jOAuthClientProperties oschina) {
		this.oschina = oschina;
	}

	public Pac4jOAuthClientProperties getPaypal() {
		return paypal;
	}

	public void setPaypal(Pac4jOAuthClientProperties paypal) {
		this.paypal = paypal;
	}

	public Pac4jOAuthClientProperties getQq() {
		return qq;
	}

	public void setQq(Pac4jOAuthClientProperties qq) {
		this.qq = qq;
	}

	public Pac4jOAuthClientProperties getRenren() {
		return renren;
	}

	public void setRenren(Pac4jOAuthClientProperties renren) {
		this.renren = renren;
	}

	public Pac4jOAuthStravaClientProperties getStrava() {
		return strava;
	}

	public void setStrava(Pac4jOAuthStravaClientProperties strava) {
		this.strava = strava;
	}

	public Pac4jOAuthClientProperties getSohu() {
		return sohu;
	}

	public void setSohu(Pac4jOAuthClientProperties sohu) {
		this.sohu = sohu;
	}

	public Pac4jOAuthClientProperties getTwitter() {
		return twitter;
	}

	public void setTwitter(Pac4jOAuthClientProperties twitter) {
		this.twitter = twitter;
	}

	public Pac4jOAuthClientProperties getVk() {
		return vk;
	}

	public void setVk(Pac4jOAuthClientProperties vk) {
		this.vk = vk;
	}

	public Pac4jOAuthClientProperties getWeibo() {
		return weibo;
	}

	public void setWeibo(Pac4jOAuthClientProperties weibo) {
		this.weibo = weibo;
	}

	public Pac4jOAuthClientProperties getWeixin() {
		return weixin;
	}

	public void setWeixin(Pac4jOAuthClientProperties weixin) {
		this.weixin = weixin;
	}

	public Pac4jOAuthClientProperties getWindowslive() {
		return windowslive;
	}

	public void setWindowslive(Pac4jOAuthClientProperties windowslive) {
		this.windowslive = windowslive;
	}

	public Pac4jOAuthClientProperties getWordpress() {
		return wordpress;
	}

	public void setWordpress(Pac4jOAuthClientProperties wordpress) {
		this.wordpress = wordpress;
	}

	public Pac4jOAuthClientProperties getYahoo() {
		return yahoo;
	}

	public void setYahoo(Pac4jOAuthClientProperties yahoo) {
		this.yahoo = yahoo;
	}

}
