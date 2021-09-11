package coindesk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/");
		// registry.addResourceHandler("/manifest.json").addResourceLocations("/WEB-INF/manifest.json");
		// registry.addResourceHandler("/service-worker.js").addResourceLocations("/WEB-INF/service-worker.js");
	}
}