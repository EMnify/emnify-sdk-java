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


package com.emnify.sdk.api;

import com.emnify.sdk.ApiException;
import com.emnify.sdk.model.Authentication;
import com.emnify.sdk.model.AuthenticationResponse;
import com.emnify.sdk.model.InlineResponse404;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for AuthenticationApi
 */
@Ignore
public class AuthenticationApiTest {

    private final AuthenticationApi api = new AuthenticationApi();

    
    /**
     * Retrieve Authentication Token
     *
     * This entrypoint returns a JWT &#x60;auth_token&#x60; for authenticating further requests to the API. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void authenticateTest() throws ApiException {
        Authentication authentication = null;
        AuthenticationResponse response = api.authenticate(authentication);

        // TODO: test validations
    }
    
}
