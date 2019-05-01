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
package org.pac4j.spring.boot.ext.property;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(Pac4jJwtProperties.PREFIX)
public class Pac4jJwtProperties {

	public static final String AUTHORIZATION_HEADER = "X-Authorization";
	public static final String AUTHORIZATION_PARAM = "token";
	public static final String PREFIX = "pac4j.jwt";

	public enum JWEAlgorithm {
		
		/**
		 * RSAES using Optimal Asymmetric Encryption Padding (OAEP) (RFC 3447),
		 * with the SHA-256 hash function and the MGF1 with SHA-256 mask
		 * generation function.
		 */
		RSA_OAEP_256("RSA-OAEP-256"),
		/**
		 * Advanced Encryption Standard (AES) Key Wrap Algorithm (RFC 3394) 
		 * using 128 bit keys.
		 */
		A128KW("A128KW"),
		/**
		 * Advanced Encryption Standard (AES) Key Wrap Algorithm (RFC 3394)
		 * using 192 bit keys.
		 */
		A192KW("A192KW"),
		/**
		 * Advanced Encryption Standard (AES) Key Wrap Algorithm (RFC 3394) 
		 * using 256 bit keys.
		 */
		A256KW("A256KW"),
		/**
		 * Direct use of a shared symmetric key as the Content Encryption Key 
		 * (CEK) for the block encryption step (rather than using the symmetric
		 * key to wrap the CEK).
		 */
		DIR("dir"),
		/**
		 * Elliptic Curve Diffie-Hellman Ephemeral Static (RFC 6090) key 
		 * agreement using the Concat KDF, as defined in section 5.8.1 of
		 * NIST.800-56A, with the agreed-upon key being used directly as the 
		 * Content Encryption Key (CEK) (rather than being used to wrap the 
		 * CEK).
		 */
		ECDH_ES("ECDH-ES"),
		/**
		 * Elliptic Curve Diffie-Hellman Ephemeral Static key agreement per
		 * "ECDH-ES", but where the agreed-upon key is used to wrap the Content
		 * Encryption Key (CEK) with the "A128KW" function (rather than being 
		 * used directly as the CEK).
		 */
		ECDH_ES_A128KW("ECDH-ES+A128KW"),
		/**
		 * Elliptic Curve Diffie-Hellman Ephemeral Static key agreement per
		 * "ECDH-ES", but where the agreed-upon key is used to wrap the Content
		 * Encryption Key (CEK) with the "A192KW" function (rather than being
		 * used directly as the CEK).
		 */
		ECDH_ES_A192KW("ECDH-ES+A192KW"),
		/**
		 * Elliptic Curve Diffie-Hellman Ephemeral Static key agreement per
		 * "ECDH-ES", but where the agreed-upon key is used to wrap the Content
		 * Encryption Key (CEK) with the "A256KW" function (rather than being 
		 * used directly as the CEK).
		 */
		ECDH_ES_A256KW("ECDH-ES+A256KW"),
		/**
		 * AES in Galois/Counter Mode (GCM) (NIST.800-38D) 128 bit keys.
		 */
		A128GCMKW("A128GCMKW"),
		/**
		 * AES in Galois/Counter Mode (GCM) (NIST.800-38D) 192 bit keys.
		 */
		A192GCMKW("A192GCMKW"),
		/**
		 * AES in Galois/Counter Mode (GCM) (NIST.800-38D) 256 bit keys.
		 */
		A256GCMKW("A256GCMKW"),
		/**
		 * PBES2 (RFC 2898) with HMAC SHA-256 as the PRF and AES Key Wrap
		 * (RFC 3394) using 128 bit keys for the encryption scheme.
		 */
		PBES2_HS256_A128KW("PBES2-HS256+A128KW"),
		/**
		 * PBES2 (RFC 2898) with HMAC SHA-384 as the PRF and AES Key Wrap
		 * (RFC 3394) using 192 bit keys for the encryption scheme.
		 */
		PBES2_HS384_A192KW("PBES2-HS384+A192KW"),
		/**
		 * PBES2 (RFC 2898) with HMAC SHA-512 as the PRF and AES Key Wrap
		 * (RFC 3394) using 256 bit keys for the encryption scheme.
		 */
		PBES2_HS512_A256KW("PBES2-HS512+A256KW");

		private final String algorithm;

		JWEAlgorithm(String algorithm) {
			this.algorithm = algorithm;
		}

		public String value() {
			return algorithm;
		}

	}
	
	public enum JWSAlgorithm {
		/**
		 * HMAC using SHA-256 hash algorithm (required).
		 */
		HS256("HS256"),
		/**
		 * HMAC using SHA-384 hash algorithm (optional).
		 */
		HS384("HS384"),
		/**
		 * HMAC using SHA-512 hash algorithm (optional).
		 */
		HS512("HS512"),
		/**
		 * RSASSA-PKCS-v1_5 using SHA-256 hash algorithm (recommended).
		 */
		RS256("RS256"),
		/**
		 * RSASSA-PKCS-v1_5 using SHA-384 hash algorithm (optional).
		 */
		RS384("RS384"),
		/**
		 * RSASSA-PKCS-v1_5 using SHA-512 hash algorithm (optional).
		 */
		RS512("RS512"),
		/**
		 * ECDSA using P-256 (secp256r1) curve and SHA-256 hash algorithm (recommended).
		 */
		ES256("ES256"),
		/**
		 * ECDSA using P-256K (secp256k1) curve and SHA-256 hash algorithm (optional).
		 */
		ES256K("ES256K"),
		/**
		 * ECDSA using P-384 curve and SHA-384 hash algorithm (optional).
		 */
		ES384("ES384"),
		/**
		 * ECDSA using P-521 curve and SHA-512 hash algorithm (optional).
		 */
		ES512("ES512"),
		/**
		 * RSASSA-PSS using SHA-256 hash algorithm and MGF1 mask generation function
		 * with SHA-256 (optional).
		 */
		PS256("PS256"),
		/**
		 * RSASSA-PSS using SHA-384 hash algorithm and MGF1 mask generation function
		 * with SHA-384 (optional).
		 */
		PS384("PS384"),
		/**
		 * RSASSA-PSS using SHA-512 hash algorithm and MGF1 mask generation function
		 * with SHA-512 (optional).
		 */
		PS512("PS512"),
		/**
		 * EdDSA signature algorithms (optional).
		 */
		EdDSA("EdDSA");

		private final String algorithm;

		JWSAlgorithm(String algorithm) {
			this.algorithm = algorithm;
		}

		public String value() {
			return algorithm;
		}

	}

	public enum EncryptionMethod {

		/**
		 * AES_128_CBC_HMAC_SHA_256 authenticated encryption using a 256 bit key
		 * (required).
		 */
		A128CBC_HS256("A128CBC-HS256"),
		/**
		 * AES_192_CBC_HMAC_SHA_384 authenticated encryption using a 384 bit key
		 * (optional).
		 */
		A192CBC_HS384("A192CBC-HS384"),
		/**
		 * AES_256_CBC_HMAC_SHA_512 authenticated encryption using a 512 bit key
		 * (required).
		 */
		A256CBC_HS512("A256CBC-HS512"),
		/**
		 * AES_128_CBC_HMAC_SHA_256 authenticated encryption using a 256 bit key,
		 * deprecated in JOSE draft suite version 09.
		 */
		A128CBC_HS256_DEPRECATED("A128CBC+HS256"),
		/**
		 * AES_256_CBC_HMAC_SHA_512 authenticated encryption using a 512 bit key,
		 * deprecated in JOSE draft suite version 09.
		 */
		A256CBC_HS512_DEPRECATED("A256CBC+HS512"),
		/**
		 * AES in Galois/Counter Mode (GCM) (NIST.800-38D) using a 128 bit key
		 * (recommended).
		 */
		A128GCM("A128GCM"),
		/**
		 * AES in Galois/Counter Mode (GCM) (NIST.800-38D) using a 192 bit key
		 * (optional).
		 */
		A192GCM("A192GCM"),
		/**
		 * AES in Galois/Counter Mode (GCM) (NIST.800-38D) using a 256 bit key
		 * (recommended).
		 */
		A256GCM("A256GCM");

		private final String algorithm;

		EncryptionMethod(String algorithm) {
			this.algorithm = algorithm;
		}

		public String value() {
			return algorithm;
		}

	}

	/** Whether Enable Pac4j Jwt. */
	private boolean enabled = false;

	private String encryptSecret;
	private String signSecret;
	
	/**
	 * JSON Web Encryption (JWE) algorithm name, represents the {@code alg} header 
	 * parameter in JWE objects. This class is immutable.
	 *
	 * <p>Includes constants for the following standard JWE algorithm names:
	 *
	 * <ul>
	 *     <li>{@link #RSA_OAEP_256 RSA-OAEP-256}
	 *     <li>{@link #RSA_OAEP RSA-OAEP} (deprecated)
	 *     <li>{@link #RSA1_5} (deprecated)
	 *     <li>{@link #A128KW}
	 *     <li>{@link #A192KW}
	 *     <li>{@link #A256KW}
	 *     <li>{@link #DIR dir}
	 *     <li>{@link #ECDH_ES ECDH-ES}
	 *     <li>{@link #ECDH_ES_A128KW ESDH-ES+A128KW}
	 *     <li>{@link #ECDH_ES_A128KW ESDH-ES+A192KW}
	 *     <li>{@link #ECDH_ES_A256KW ESDH-ES+A256KW}
	 *     <li>{@link #PBES2_HS256_A128KW PBES2-HS256+A128KW}
	 *     <li>{@link #PBES2_HS384_A192KW PBES2-HS256+A192KW}
	 *     <li>{@link #PBES2_HS512_A256KW PBES2-HS256+A256KW}
	 * </ul>
	 *
	 * <p>Additional JWE algorithm names can be defined using the constructors.
	 * 
	 */
	private JWEAlgorithm jweAlgorithm = JWEAlgorithm.DIR;
	
	/**
	 * JSON Web Signature (JWS) algorithm name, represents the {@code alg} header
	 * parameter in JWS objects. Also used to represent integrity algorithm
	 * ({@code ia}) header parameters in JWE objects. This class is immutable.
	 *
	 * <p>
	 * Includes constants for the following standard JWS algorithm names:
	 *
	 * <ul>
	 * <li>{@link #HS256}
	 * <li>{@link #HS384}
	 * <li>{@link #HS512}
	 * <li>{@link #RS256}
	 * <li>{@link #RS384}
	 * <li>{@link #RS512}
	 * <li>{@link #ES256}
	 * <li>{@link #ES384}
	 * <li>{@link #ES512}
	 * <li>{@link #PS256}
	 * <li>{@link #PS384}
	 * <li>{@link #PS512}
	 * <li>{@link #EdDSA}
	 * <li>{@link #ES256K} (non-standard)
	 * </ul>
	 *
	 * <p>
	 * Additional JWS algorithm names can be defined using the constructors.
	 */
	private JWSAlgorithm jwsAlgorithm = JWSAlgorithm.HS256;

	/**
	 * Encryption method name, represents the {@code enc} header parameter in JSON
	 * Web Encryption (JWE) objects. This class is immutable.
	 *
	 * <p>
	 * Includes constants for the following standard encryption method names:
	 *
	 * <ul>
	 * <li>{@link #A128CBC_HS256 A128CBC-HS256}
	 * <li>{@link #A192CBC_HS384 A192CBC-HS384}
	 * <li>{@link #A256CBC_HS512 A256CBC-HS512}
	 * <li>{@link #A128GCM}
	 * <li>{@link #A192GCM}
	 * <li>{@link #A256GCM}
	 * <li>{@link #A128CBC_HS256_DEPRECATED A128CBC+HS256 (deprecated)}
	 * <li>{@link #A256CBC_HS512_DEPRECATED A256CBC+HS512 (deprecated)}
	 * </ul>
	 *
	 * <p>
	 * Additional encryption method names can be defined using the constructors.
	 *
	 */
	private EncryptionMethod encryption = EncryptionMethod.A256GCM;
	
    private Map<String, Object> customProperties = new LinkedHashMap<>();
	
	/**
	 * HTTP Authorization header, equal to <code>X-Authorization</code>
	 */
	private String authorizationHeaderName = AUTHORIZATION_HEADER;
	private String authorizationHeaderPrefix = "";
	/** The Name of Header Client. */
	private String headerClientName = "jwt-header";

	private String authorizationParamName = AUTHORIZATION_PARAM;
	private boolean supportGetRequest = true;
	private boolean supportPostRequest = true;
	/** The Name of Param Client. */
	private String paramClientName = "jwt-param";

	private String authorizationCookieName = AUTHORIZATION_PARAM;
	/** The Name of Cookie Client. */
	private String cookieClientName = "jwt-cookie";

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getEncryptSecret() {
		return encryptSecret;
	}

	public void setEncryptSecret(String encryptSecret) {
		this.encryptSecret = encryptSecret;
	}

	public String getSignSecret() {
		return signSecret;
	}

	public void setSignSecret(String signSecret) {
		this.signSecret = signSecret;
	}

	public JWEAlgorithm getJweAlgorithm() {
		return jweAlgorithm;
	}

	public void setJweAlgorithm(JWEAlgorithm jweAlgorithm) {
		this.jweAlgorithm = jweAlgorithm;
	}

	public JWSAlgorithm getJwsAlgorithm() {
		return jwsAlgorithm;
	}

	public void setJwsAlgorithm(JWSAlgorithm jwsAlgorithm) {
		this.jwsAlgorithm = jwsAlgorithm;
	}

	public EncryptionMethod getEncryption() {
		return encryption;
	}

	public void setEncryption(EncryptionMethod encryption) {
		this.encryption = encryption;
	}
	
	public Map<String, Object> getCustomProperties() {
		return customProperties;
	}

	public void setCustomProperties(Map<String, Object> customProperties) {
		this.customProperties = customProperties;
	}

	public String getAuthorizationHeaderName() {
		return authorizationHeaderName;
	}

	public void setAuthorizationHeaderName(String authorizationHeaderName) {
		this.authorizationHeaderName = authorizationHeaderName;
	}

	public String getAuthorizationHeaderPrefix() {
		return authorizationHeaderPrefix;
	}

	public void setAuthorizationHeaderPrefix(String authorizationHeaderPrefix) {
		this.authorizationHeaderPrefix = authorizationHeaderPrefix;
	}

	public String getHeaderClientName() {
		return headerClientName;
	}

	public void setHeaderClientName(String headerClientName) {
		this.headerClientName = headerClientName;
	}

	public String getAuthorizationParamName() {
		return authorizationParamName;
	}

	public void setAuthorizationParamName(String authorizationParamName) {
		this.authorizationParamName = authorizationParamName;
	}

	public boolean isSupportGetRequest() {
		return supportGetRequest;
	}

	public void setSupportGetRequest(boolean supportGetRequest) {
		this.supportGetRequest = supportGetRequest;
	}

	public boolean isSupportPostRequest() {
		return supportPostRequest;
	}

	public void setSupportPostRequest(boolean supportPostRequest) {
		this.supportPostRequest = supportPostRequest;
	}

	public String getParamClientName() {
		return paramClientName;
	}

	public void setParamClientName(String paramClientName) {
		this.paramClientName = paramClientName;
	}

	public String getAuthorizationCookieName() {
		return authorizationCookieName;
	}

	public void setAuthorizationCookieName(String authorizationCookieName) {
		this.authorizationCookieName = authorizationCookieName;
	}

	public String getCookieClientName() {
		return cookieClientName;
	}

	public void setCookieClientName(String cookieClientName) {
		this.cookieClientName = cookieClientName;
	}

}
