/*
 * Copyright 2016-2017 Red Hat, Inc, IBM, and individual contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.openliberty.example;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;


/**
 * A simple health check that verifies the server suspend state. It corresponds to the /stop operation.
 */
@Health
@ApplicationScoped
public class HealthChecks implements HealthCheck {
	
	 
    @Override
    public HealthCheckResponse call() {       
    	String url = "http://localhost:9080/api/greeting";
    	Client client = ClientBuilder.newClient();
    	Response response = client.target(url).request(MediaType.APPLICATION_JSON).get();
      

    	if (response.getStatus() == 200) {
    		return HealthCheckResponse.named("server-state").up().build();
    	} else if ( response.getStatus() == 503){
    		return HealthCheckResponse.named("server-state").down().build();
    	} else {
    		throw new RuntimeException("Unexpected status code: " + response.getStatus());
    	}
     
    }
}
