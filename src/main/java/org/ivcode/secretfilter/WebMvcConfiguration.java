package org.ivcode.secretfilter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		// redirect root requests to the swagger-ui
		registry.addViewController("/")
				.setViewName("redirect:/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config");
	}
}