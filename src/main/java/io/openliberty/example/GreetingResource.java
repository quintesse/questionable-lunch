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

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/")
public class GreetingResource {
    private static final String template = "Hello, %s!";

    private static boolean online = true;
    
    @GET
    @Path("/greeting")
    @Produces("application/json")
    public Response greeting(@QueryParam("name") @DefaultValue("World") String name) {
    	if (GreetingResource.online) { 
    		return Response.ok().entity(new Greeting(String.format(template, name))).build();
    	} else {
    		return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity("{ \"error\" : \"Service is currently offline.\" }").build();
    	}
    }

    /**
     * The /stop operation is actually just going to suspend the server inbound traffic,
     * which leads to 503 when subsequent HTTP requests are received
     */
    @GET
    @Path("/stop")
    public Response stop() {
    	GreetingResource.online = false;
    	return Response.ok("Application is no longer available").build();
    }
}
