package org.pac4j.spring.boot;

import java.util.HashMap;
import java.util.Map;

import org.pac4j.core.context.DefaultAuthorizers;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ConfigurationProperties(Pac4jProperties.PREFIX)
@Getter
@Setter
@ToString
public class Pac4jProperties {

	/* ================================== Pac4j ================================= */
	
	public static final String PREFIX = "pac4j";
	
	/**
	 * Enable Pac4j.
	 */
	private boolean enabled = false;
    
	/**
	 * The name of the server this application is hosted on. 
	 * Service URL will be dynamically constructed using this,
	 * i.e. https://localhost:8443 (you must include the protocol, but port is optional if it's a standard port).
	 */
	private String serviceUrl;
	/** 
	 * The location of the client callback URL, i.e. https://localhost:8080/myapp/callback 
	 */
	private String callbackUrl;
	/** 
	 * The location of the default access URL, i.e. https://localhost:8080/myapp/index.html 
	 */
	private String defaultUrl;
	
	/** 认证IP正则表达式：可实现IP访问限制 */
    private String allowedIpRegexpPattern;
    
    private String[] allowedHttpMethods;
	
    /**
	 * If <code>true</code>, will always redirect to the value of {@code callbackUrl}
	 * (defaults to <code>false</code>).
	 */
	private boolean callbackUrlFixed = false;
    
    /** SecurityFilter */
    
    /** List of clients for authentication. 启用认证的客户端类型 */
    private String clients;
    
    /** Specifies the name of the request parameter on where to find the clientName (i.e. client_name). */
  	private String clientParameterName = "client_name";
	
  	private String defaultClientName;
    
    /** 
     * List of authorizers. 可指定多个，通,分割 ; 每个名称对应一个实现类，除了默认的实现，也可自己定义实现 <br/>
     * @see org.pac4j.core.context.DefaultAuthorizers <br/>
     * @see org.pac4j.core.authorization.checker.DefaultAuthorizationChecker <br/>
     * <table style="border-collapse: collapse; border: 1px; width: 100%; table-layout: fixed;" class="aa" cellspacing="0" cellpadding="0" border="1">
	 *	  <tbody>
	 *	  	<tr>
	 *			<td style="padding: 0cm 5.4pt 0cm 5.4pt; width: 150px;">
	 *			<p class="MsoNormal">Authorizer名称</p>
	 *			</td>
	 *			<td style="padding: 0cm 5.4pt 0cm 5.4pt; ">
	 *			<p class="MsoNormal">Authorizer实现类</p>
	 *			</td>
	 *			<td style="padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">说明（括号里的表示备注）</p>
	 *			</td>
	 *		</tr>
	 *		<tr>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal"><strong>身份验证相关的</strong></p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">&nbsp;</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">&nbsp;</p>
	 *			</td>
	 *		</tr>
	 *		<tr>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">allowAjaxRequests</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">org.pac4j.core.authorization.authorizer.CorsAuthorizer</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">Define how the CORS requests are authorized.</p>
	 *			</td>
	 *		</tr>
	 *		<tr>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">csrf</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">org.pac4j.core.authorization.authorizer.csrf.CsrfAuthorizer</p>
	 *			<p class="MsoNormal">org.pac4j.core.authorization.authorizer.csrf.CsrfTokenGeneratorAuthorizer</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">Authorizer that checks CSRF tokens.</p>
	 *			<p class="MsoNormal">Authorizer which creates a new CSRF token and adds it as a request attribute and as a cookie (AngularJS).</p>
	 *			</td>
	 *		</tr>
	 *		<tr>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">csrfCheck</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">org.pac4j.core.authorization.authorizer.csrf.CsrfAuthorizer</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">Authorizer that checks CSRF tokens.</p>
	 *			</td>
	 *		</tr>
	 *		<tr>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">csrfToken</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">org.pac4j.core.authorization.authorizer.csrf.CsrfTokenGeneratorAuthorizer</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">Authorizer which creates a new CSRF token and adds it as a request attribute and as a cookie (AngularJS).</p>
	 *			</td>
	 *		</tr>
	 *		<tr>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">hsts</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">org.pac4j.core.authorization.authorizer.StrictTransportSecurityHeader</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">Strict transport security header（指定https访问）.</p>
	 *			</td>
	 *		</tr>
	 *		<tr>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">nocache</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">org.pac4j.core.authorization.authorizer.CacheControlHeader</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">Cache control header（添加不缓存策略）.</p>
	 *			</td>
	 *		</tr>
	 *		<tr>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">noframe</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">org.pac4j.core.authorization.authorizer.XFrameOptionsHeader</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">XFrame options header（添加xframe安全策略）.</p>
	 *			</td>
	 *		</tr>
	 *		<tr>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">nosniff</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">org.pac4j.core.authorization.authorizer.XContentTypeOptionsHeader</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">XContent type options header（添加nosniff安全策略）.</p>
	 *			</td>
	 *		</tr>
	 *		<tr>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">securityheaders</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">org.pac4j.core.authorization.authorizer.CacheControlHeader</p>
	 *			<p class="MsoNormal">org.pac4j.core.authorization.authorizer.XContentTypeOptionsHeader</p>
	 *			<p class="MsoNormal">org.pac4j.core.authorization.authorizer.StrictTransportSecurityHeader</p>
	 *			<p class="MsoNormal">org.pac4j.core.authorization.authorizer.XFrameOptionsHeader</p>
	 *			<p class="MsoNormal">org.pac4j.core.authorization.authorizer.XSSProtectionHeader</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">Cache control header（添加不缓存策略）.</p>
	 *			<p class="MsoNormal">XContent type options header（添加nosniff安全策略）.</p>
	 *			<p class="MsoNormal">Strict transport security header（指定https访问）.</p>
	 *			<p class="MsoNormal">XFrame options header（添加xframe安全策略）.</p>
	 *			<p class="MsoNormal">XSS protection header（添加xss安全策略）.</p>
	 *			</td>
	 *		</tr>
	 *		<tr>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">xssprotection</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">org.pac4j.core.authorization.authorizer.XSSProtectionHeader</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">XSS protection header（添加xss安全头控制）.</p>
	 *			</td>
	 *		</tr>
	 *		<tr>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">method</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">org.pac4j.core.authorization.authorizer.CheckHttpMethodAuthorizer</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">Checks the HTTP method（添加请求方式检查策略）.</p>
	 *			</td>
	 *		</tr>
	 *		<tr>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal"><strong>授权相关的</strong></p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">&nbsp;</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">&nbsp;</p>
	 *			</td>
	 *		</tr>
	 *		<tr>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">isAnonymous</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">org.pac4j.core.authorization.authorizer.IsAnonymousAuthorizer</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">The user must be anonymous. To protect resources like a login page.</p>
	 *			</td>
	 *		</tr>
	 *		<tr>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">isAuthenticated</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">org.pac4j.core.authorization.authorizer.IsAuthenticatedAuthorizer</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">The user must be authenticated. This authorizer should never be necessary unless using the org.pac4j.core.client.direct.AnonymousClient.</p>
	 *			</td>
	 *		</tr>
	 *		<tr>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">isFullyAuthenticated</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">org.pac4j.core.authorization.authorizer.IsFullyAuthenticatedAuthorizer</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">The user must be fully authenticated (not remembered).</p>
	 *			</td>
	 *		</tr>
	 *		<tr>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">isIPAuthenticated</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">org.pac4j.http.authorization.authorizer.IpRegexpAuthorizer</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">Authorizes users based on their IP and a regexp pattern.</p>
	 *			</td>
	 *		</tr>
	 *		<tr>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">isRemembered</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">org.pac4j.core.authorization.authorizer.IsRememberedAuthorizer</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">The user must be authenticated and remembered.</p>
	 *			</td>
	 *		</tr>
	 *		<tr>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal"><strong>其他</strong></p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">&nbsp;</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">&nbsp;</p>
	 *			</td>
	 *		</tr>
	 *		<tr>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">noSessionCreation</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">org.apache.shiro.web.filter.session</p>
	 *			<p class="MsoNormal">.NoSessionCreationFilter</p>
	 *			</td>
	 *			<td style=" padding: 0cm 5.4pt 0cm 5.4pt;">
	 *			<p class="MsoNormal">不创建会话拦截器，调用 subject.getSession(false)不会有什么问题，但是如果 subject.getSession(true)将抛出 DisabledSessionException异常；</p>
	 *			</td>
	 *		</tr>
	 *	  </tbody>
	 * </table>
     */
    private String authorizers = DefaultAuthorizers.CSRF;
    private String matchers;
    
    /** Whether multiple profiles should be kept . 默认 false*/
    private boolean multiProfile = false;
    
	/**
	 * If <tt>true</tt>, causes any redirection URLs to be calculated minus the protocol and context path (defaults to <tt>false</tt>).
	 */
    boolean completeRelativeUrl = false;

    /* Map containing user defined parameters */
    private Map<String, String> customParams = new HashMap<>();
    

}
