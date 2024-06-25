package garlicbears._quiz.common.config.jwt;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import garlicbears._quiz.common.config.auth.PrincipalDetails;
import garlicbears._quiz.common.entity.Active;
import garlicbears._quiz.common.entity.User;
import garlicbears._quiz.management.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	private final UserRepository userRepository;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
		super(authenticationManager);
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {
		String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);

		if (jwtHeader != null && jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
			String token = jwtHeader.replace(JwtProperties.TOKEN_PREFIX, "");

			String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
				.build()
				.verify(token)
				.getClaim("username")
				.asString();

			if (username != null) {
				Optional<User> userEntity = userRepository.findByUserEmail(username)
					.stream()
					.filter(user -> user.getUserActive() == Active.active)
					.findFirst();
				if (userEntity.isEmpty()) {
					throw new UsernameNotFoundException("not found userEmail : " + username);
				}

				PrincipalDetails principalDetails = new PrincipalDetails(userEntity.get());

				Authentication auth = new UsernamePasswordAuthenticationToken(principalDetails, null,
					principalDetails.getAuthorities());

				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		chain.doFilter(request, response);
	}
}
