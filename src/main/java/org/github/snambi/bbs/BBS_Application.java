package org.github.snambi.bbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Starts the application.
 * NOTE : DO not add @EnableWebMvc it breaks static content delivery ( no idea why )
 */
@SpringBootApplication
public class BBS_Application extends SpringBootServletInitializer {


	public static void main(String[] args) {
		SpringApplication.run(BBS_Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BBS_Application.class);
	}
}
