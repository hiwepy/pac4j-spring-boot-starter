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
package org.pac4j.spring.boot;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.config.CasProtocol;
import org.pac4j.core.context.HttpConstants;
import org.pac4j.core.context.Pac4jConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ConfigurationProperties(Pac4jCasProperties.PREFIX)
@Getter
@Setter
@ToString
public class Pac4jCasProperties {

	public static final String PREFIX = "pac4j.cas";

	/* ================================== Cas Client ================================= */
	
	// default name of the CAS attribute for remember me authentication (CAS 3.4.10+)
    public static final String DEFAULT_REMEMBER_ME_ATTRIBUTE_NAME = "longTermAuthenticationRequestTokenUsed";
	
	/** Defines the location of the CAS server login URL, i.e. https://localhost:8443/cas/login */
	private String loginUrl;
	/** Defines the location of the CAS server logout URL, i.e. https://localhost:8443/cas/logout */
	private String logoutUrl;
	/** Defines the location of the CAS server rest URL, i.e. https://localhost:8443/cas/v1/tickets */
	private String restUrl;
	/** The prefix url of the CAS server. i.e.https://localhost:8443/cas */
	private String prefixUrl;
	   
	/** 
	 * Specifies the name of the request parameter on where to find the service (i.e. service). 
	 */
	private String serviceParameterName = CasConfiguration.SERVICE_PARAMETER;
	
    /** Specifies whether any proxy is OK. Defaults to false. */
	private boolean acceptAnyProxy = false;
	
	/**
	 * Specifies the proxy chain. 
	 * Each acceptable proxy chain should include a space-separated list of URLs (for exact match) or 
	 * regular expressions of URLs (starting by the ^ character). 
	 * Each acceptable proxy chain should appear on its own line.
	 */
	private String allowedProxyChains;
	
	/** 
	 * Specifies the encoding charset the client should use 
	 */
    private String encoding = StandardCharsets.UTF_8.name();
	
    /** 
	 * Specifies whether gateway=true should be sent to the CAS server. Valid values are either true/false (or no value at all) 
	 */
	private boolean gateway = false;
	
	/**
	 * Specifies whether renew=true should be sent to the CAS server. 
	 * Valid values are either true/false (or no value at all). 
	 * Note that renew cannot be specified as local init-param setting..
	 */
	private boolean renew = false;

	/**
	 * The name of the server this application is hosted on. 
	 * Service URL will be dynamically constructed using this,
	 * i.e. https://localhost:8443 (you must include the protocol, but port is optional if it's a standard port).
	 */
	private String serviceUrl;
	
	/**
	 * The tolerance for drifting clocks when validating SAML tickets. 
	 * Note that 10 seconds should be more than enough for most environments that have NTP time synchronization. 
	 * Defaults to 1000 msec
	 */
	private long tolerance = 1000L;

    private String postLogoutUrlParameter = CasConfiguration.SERVICE_PARAMETER;

    /* Map containing user defined parameters */
    private Map<String, String> customParams = new HashMap<>();

    private String method;
    
	/* ================================== Pac4j Cas ================================= */
	
	/** Whether Enable Pac4j Cas. */
	private boolean enabled = false;
	
	/** The protocol of the CAS Client. */
	private CasProtocol protocol = CasProtocol.CAS30;
	
    /** CasClient */
	private boolean casClient = false;
    private String casClientName = "cas";
    
    /** DirectCasClient */
    
    private boolean directCasClient = false;
    private String directCasClientName = "direct-cas";
    
    /** DirectCasProxyClient */
    
    private boolean directCasProxyClient = false;
    private String directCasProxyClientName = "direct-cas-proxy";
    
    /** CasRestBasicAuthClient */
    
    private boolean casRestBasicAuthClient = false;
    private String casRestBasicAuthClientName = "cas-rest-basic";
    private String headerName = HttpConstants.AUTHORIZATION_HEADER;
    private String prefixHeader = HttpConstants.BASIC_HEADER_PREFIX;
    
    /** CasRestFormClient */
    
    private boolean casRestFormClient = false;
    private String casRestFormClientName = "cas-rest-form";
    private String usernameParameterName = Pac4jConstants.USERNAME;
    private String passwordParameterName = Pac4jConstants.PASSWORD;
	
}
