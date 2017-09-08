package com.thesis.tremor;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.thesis.tremor.rest.endpoint.SecurityEndpoint;
import com.thesis.tremor.rest.endpoint.SessionEndpoint;
import com.thesis.tremor.rest.endpoint.TestEndpoint;
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
		
		register(MultiPartFeature.class);
		
		register(SecurityEndpoint.class);
		register(UserEndpoint.class);
		register(SessionEndpoint.class);
		register(TestEndpoint.class);
	}
}
