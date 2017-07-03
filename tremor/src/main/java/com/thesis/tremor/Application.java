package com.thesis.tremor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author  Adrian Jasper K. Chua
 * @version 1.0
 * @since   Jul 3, 2017
 */
@SpringBootApplication
@ImportResource({"classpath:META-INF/spring/hibernate.xml"})
public class Application {

	public static void main(String ... args) {
		SpringApplication.run(Application.class, args);
	}
}
