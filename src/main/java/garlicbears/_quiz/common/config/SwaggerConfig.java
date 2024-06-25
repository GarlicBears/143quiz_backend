package garlicbears._quiz.common.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI openApi() {
		return new OpenAPI()
			.addSecurityItem(new SecurityRequirement().addList("JWT"))
			.components(new Components().addSecuritySchemes("JWT", createApiKeyScheme()))
			.info(new Info().title("Garlic Bears 143quiz API").description("This is How API").version("v0.0.1"));
	}

	@Bean
	public GroupedOpenApi userApi() {
		return GroupedOpenApi
			.builder()
			.group("1.유저")
			.pathsToMatch("/user/**", "/game/**")
			.build();
	}

	@Bean
	public GroupedOpenApi adminApi() {
		return GroupedOpenApi
			.builder()
			.group("2.관리자")
			.pathsToMatch("/admin/**")
			.build();
	}

	private SecurityScheme createApiKeyScheme() {
		return new SecurityScheme().type(SecurityScheme.Type.HTTP).bearerFormat("JWT").scheme("bearer");
	}
}
