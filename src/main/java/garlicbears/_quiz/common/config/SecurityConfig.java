package garlicbears._quiz.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

import garlicbears._quiz.common.config.jwt.JwtAuthenticationFilter;
import garlicbears._quiz.common.config.jwt.JwtAuthorizationFilter;
import garlicbears._quiz.management.user.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final CorsFilter corsFilter;
	private final UserRepository userRepository;

	public SecurityConfig(CorsFilter corsFilter, UserRepository userRepository) {
		this.corsFilter = corsFilter;
		this.userRepository = userRepository;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder sharedObject = http.getSharedObject(AuthenticationManagerBuilder.class);
		AuthenticationManager authenticationManager = sharedObject.build();
		http.authenticationManager(authenticationManager);

		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager);
		jwtAuthenticationFilter.setFilterProcessesUrl("/user/login");

		http.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilter(corsFilter)
			.addFilter(jwtAuthenticationFilter)
			.addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository))
			.authorizeHttpRequests(
				auth -> auth.requestMatchers("/user/signup", "/user/checkEmail", "/user/checkNickname")
					.permitAll()
					.requestMatchers("/user/**")
					.authenticated()
					//                        .requestMatchers("/api/v1/manager/**").hasAnyRole("ADMIN", "MANAGER")
					//                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
					.anyRequest()
					.permitAll());

		return http.build();
	}
}
