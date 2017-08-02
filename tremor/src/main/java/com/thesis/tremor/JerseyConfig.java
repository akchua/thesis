package com.thesis.tremor;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.thesis.tremor.rest.endpoint.SecurityEndpoint;
import com.thesis.tremor.rest.endpoint.UserEndpoint;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   Jul 3, 2017
 */
@Component
@ApplicationPath("services")
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		
		register(SecurityEndpoint.class);
		register(UserEndpoint.class);
	}
}
