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
import com.emnify.sdk.model.ChangePassword422response;
import com.emnify.sdk.model.ChangeQuota422Response;
import com.emnify.sdk.model.Endpoint;
import com.emnify.sdk.model.EndpointQuota;
import com.emnify.sdk.model.Model40xResponse;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for EndpointApi
 */
@Ignore
public class EndpointApiTest {

    private final EndpointApi api = new EndpointApi();

    
    /**
     * Create Endpoint
     *
     * If a &#x60;sim&#x60; object is provided, the SIM with the contained ID will be assigned to the endpoint. The &#x60;activate&#x60; property defaults to &#x60;true&#x60; and can be omitted unless the SIM should not be activated with this API call.  The following fields may be provided: * &#x60;name&#x60; (String required) * &#x60;service_profile&#x60; (Object required) * &#x60;tariff_profile&#x60; (Object required) * &#x60;status&#x60; (Object required) - &#x60;0&#x60; &#x3D; __Enabled__, &#x60;1&#x60; &#x3D; __Disabled__! * &#x60;tags&#x60; (String optional) * &#x60;imei&#x60; (String optional) * &#x60;imei_lock&#x60; (Boolean optional) * &#x60;sim&#x60; (Object optional)   - &#x60;id&#x60; (number required) SIM ID to be assigned to this endpoint   - &#x60;activate&#x60; (Boolean, optional, default:true) * &#x60;ip_address&#x60; (String optional) * &#x60;ip_address_space&#x60; (Object, optional if IP address is omitted, mandatory when IP address is set) 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void createEndpointTest() throws ApiException {
        Endpoint endpoint = null;
        api.createEndpoint(endpoint);

        // TODO: test validations
    }
    
    /**
     * Remove Data Quota
     *
     * Will delete the data quota for the endpoint, if any is set. Note that if &#x60;apply_data_quota&#x60; is still set in the service profile, the endpoint will get blocked from data service. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void deleteEndpointDataQuotaByIdTest() throws ApiException {
        Integer endpointId = null;
        api.deleteEndpointDataQuotaById(endpointId);

        // TODO: test validations
    }
    
    /**
     * Retrieve Data Quota details
     *
     * Returns details about the assigned Data Quota for an endpoint. * &#x60;status&#x60;: this indicates the current status of the quota and may contain the following values:     - &#x60;ACTIVE&#x60;: the endpoint can currently connect and has quota left     - &#x60;EXHAUSTED&#x60;: the endpoint has exceeded the quota volume, if it still can use data service depends on the action chosen to be performed on exhaustion     - &#x60;EXPIRED&#x60;: the quota has expired; the endpoint is denied from using data services (until new quota is added) * &#x60;volume&#x60;: returns the volume left on this quota in MB * &#x60;expiry_date&#x60;: timestamp when this quota will expire and the endpoint will definitely be denied from using further data services (regardless if the quota volume was used up or not) * &#x60;peak_throughput&#x60;: The maximum bandwidth in octets per second after the endpoint has been throttled. * &#x60;action_on_exhaustion&#x60;: returns the behaviour defined to be applied when quota volume is used up (exhausted).     - &#x60;Throttle&#x60;: bandwidth will be throttle to the defined peak throughput until quota expires     - &#x60;Block&#x60;: data service will be instantly blocked once volume used up, regardless if the expiry date is already reached or not * &#x60;auto_refill&#x60;: 0 (false) / 1 (true), refill the quota with the last added volume on a daily basis 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void endpointQuotaDataByEndpointIdGetTest() throws ApiException {
        Integer endpointId = null;
        EndpointQuota response = api.endpointQuotaDataByEndpointIdGet(endpointId);

        // TODO: test validations
    }
    
    /**
     * Set Data Quota
     *
     * At any time, a new data quota can be set for an endpoint. At an initial state when no data quota is set, the endpoint will be denied from using data services. To top-up the data volume you need to retrieve the currently remaining volume, increase it by the top-up volume and set it as the new quota volume.  The following parameters can be configured: * &#x60;status&#x60; - The status of the quota (mandatory):   - 1: &#x60;ACTIVE&#x60;   - 2: &#x60;EXHAUSTED&#x60;   - 3: &#x60;EXPIRED&#x60; * &#x60;volume&#x60;: The volume left on this quota in MB * &#x60;expiry_date&#x60;: Timestamp when this quota will expire and the endpoint will definitely be denied from using further data services (mandatory) * &#x60;auto_refill&#x60;: Wether the quota shall be refilled on a daily basis (defaults to disabled):   - 0: &#x60;disabled&#x60;   - 1: &#x60;enabled&#x60; * &#x60;threshold_percentage&#x60;: The percentage of remaining quota at which the system should generate a &#x60;threshold reached&#x60; event * &#x60;action_on_exhaustion&#x60;: The behaviour of the system after the quota is exhausted:   - id: ID of the action on quota exhaustion (mandatory)     - 1: &#x60;Block&#x60;     - 2: &#x60;Throttle&#x60;   - peak_throughput: The maximum bandwidth in octets per second after the endpoint has been throttled. (mandatory)   Allowed values are 64000, 128000, 256000, 384000. (will not take any effect on &#x60;action_on_exhaustion&#x60; \&quot;Block\&quot;)  #### Events The system generates a \&quot;Quota Used Up\&quot; Event in case the data quota is completely depleted. The endpoint will be blocked from further consumption of data. The quota object will be included in the details of the event. Example events can be found in the Events of an Endpoint section.  #### Notes  The endpoint can instantly use data services after the API call to this entrypoint is successfully made. Any timestamp with a future date can be set, this allows to create data packages (e.g. for 1 hour, 24 hour, 7 days or any other timeframe) as required. 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void endpointQuotaDataByEndpointIdPostTest() throws ApiException {
        Integer endpointId = null;
        EndpointQuota endpointQuota = null;
        api.endpointQuotaDataByEndpointIdPost(endpointId, endpointQuota);

        // TODO: test validations
    }
    
    /**
     * List Endpoints
     *
     * Returns the list of endpoints, filtered, sorted and paged according to query parameters.
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getEndpointsTest() throws ApiException {
        String q = null;
        String sort = null;
        Integer page = null;
        Integer perPage = null;
        List<Endpoint> response = api.getEndpoints(q, sort, page, perPage);

        // TODO: test validations
    }
    
}
