/*-
 * #%L
 * EMnify Java SDK
 * %%
 * Copyright (C) 2021 EMnify
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package com.emnify.sdk.client;

import java.util.Objects;

import com.emnify.sdk.ApiClient;
import com.emnify.sdk.client.auth.Authentication;
import com.emnify.sdk.client.config.Configuration;
import com.emnify.sdk.client.exception.ClientException;
import com.emnify.sdk.client.retrier.AuthenticationRetrier;
import org.apache.commons.lang3.SystemUtils;
import static com.emnify.sdk.client.config.Configuration.createAuthentication;

public class EMnify {

    private static final String APPLICATION_TOKEN_ENV = "EMNIFY_APPLICATION_TOKEN";
    private static final String USERNAME_ENV = "EMNIFY_USERNAME";
    private static final String PASSWORD_ENV = "EMNIFY_PASSWORD";

    private final Authentication authentication;
    private final AuthenticationRetrier authenticationRetrier;
    private final ApiClient apiClient;

    private EMnify(Authentication authentication, ApiClient apiClient) {
        this.authentication = Objects.requireNonNull(authentication);
        this.apiClient = Objects.requireNonNull(apiClient);
        this.authenticationRetrier = Configuration.createAuthenticationRetrier(authentication, apiClient);
    }

    /**
     * Performs api client authorization according to configured system environment variables:
     * <ul>
     *     <li>EMNIFY_APPLICATION_TOKEN - is used for <a href="https://cdn.emnify.net/api/doc/application-token.html" target="_blank">Application Token Authentication</a></li>
     *     <li>EMNIFY_USERNAME and EMNIFY_USERNAME - are used for <a href="https://cdn.emnify.net/api/doc/basic-auth.html" target="_blank">User Authentication</a></li>
     * </ul>
     * @return instance of authorized EMnify Client
     * @throws ClientException if authentication failed
     */
    public static EMnify authenticate() throws ClientException {
        Authentication authentication;
        authentication = createAuthentication(SystemUtils.getEnvironmentVariable(APPLICATION_TOKEN_ENV, ""));

        if (authentication == null) {
            String username = SystemUtils.getEnvironmentVariable(USERNAME_ENV, "");
            String password = SystemUtils.getEnvironmentVariable(PASSWORD_ENV, "");
            authentication = Configuration.createAuthentication(username, password);
        }

        return authenticate(authentication);
    }

    /**
     * Performs api client authorization with user/password combination
     *
     * @param username username
     * @param password password
     *
     * @return instance of authorized EMnify Client
     * @throws ClientException if authentication failed
     */
    public static EMnify authenticate(String username, String password) throws ClientException {
        return authenticate(Configuration.createAuthentication(username, password));
    }

    /**
     * Performs api client authorization with application token
     *
     * @param appToken application token value
     *
     * @return instance of authorized EMnify Client
     * @throws ClientException if authentication failed
     */
    public static EMnify authenticate(String appToken) throws ClientException {
        return authenticate(createAuthentication(appToken));
    }

    private static EMnify authenticate(Authentication authentication) throws ClientException {
        if (authentication == null) {
            throw new ClientException("Authentication configuration is wrong.");
        }
        ApiClient apiClient = Configuration.getApiClient();
        authentication.authenticate(apiClient);
        return new EMnify(authentication, apiClient);
    }

    /**
     * Checks if authentication token is expired
     * @return true if auth_token is expired otherwise false
     */
    public boolean isAuthenticationExpired() {
        return authentication.isExpired();
    }

    /**
     * Refresh auth token if it is expired and refresh token is not expired
     *
     * @throws ClientException if attempt of authentication failed
     */
    public void refreshAuthIfNecessary() throws ClientException {
        if (authentication == null) {
            throw new ClientException("Refresh authentication is not possible");
        }

        authentication.authenticate(apiClient);
    }

    public EndpointClient buildEndpointClient() {
        return new EndpointClient(apiClient, authenticationRetrier);
    }

    /**
     * Returns a string representation of the object
     * This is useful for testing and debugging. Sensitive data will be redacted from this string using a placeholder value.
     * @return
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EMnify{");
        sb.append("authentication=").append(authentication);
        sb.append(", apiClient=").append(apiClient);
        sb.append(", authenticationRetrier=").append(authenticationRetrier);
        sb.append('}');
        return sb.toString();
    }
}
