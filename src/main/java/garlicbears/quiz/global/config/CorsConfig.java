package garlicbears.quiz.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOriginPattern("http://localhost:3000");
		config.addAllowedOriginPattern("https://garlicbears.com");
		config.addAllowedOriginPattern("https://garlicbears.github.io");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");

		config.addExposedHeader("Authorization"); //Header에 Authorization을 노출

		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}
