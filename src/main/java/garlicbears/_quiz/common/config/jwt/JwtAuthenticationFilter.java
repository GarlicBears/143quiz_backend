package garlicbears._quiz.common.config.jwt;

import java.io.IOException;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import garlicbears._quiz.common.config.auth.PrincipalDetails;
import garlicbears._quiz.common.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {
		// 로그인 시도할 경우 도달하게 되는 필터
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			User user = objectMapper.readValue(request.getInputStream(), User.class);

			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				user.getUserEmail(), user.getUserPassword());

			return authenticationManager.authenticate(authenticationToken);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		// 로그인 성공한 경우 도착하는 함수
		// Jwt 토큰을 발급해 헤더(Authorization)로 전달
		PrincipalDetails principalDetails = (PrincipalDetails)authResult.getPrincipal();

		// 삭제 회원의 경우 토큰을 발급해서는 안된다.
		User user = principalDetails.getUser();
		if (user.getUserActive().toString().equals("inactive")) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		String jwtToken = JWT.create()
			.withSubject("143quiz")
			.withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION))
			.withClaim("id", principalDetails.getUser().getUserId())
			.withClaim("username", principalDetails.getUser().getUserEmail())
			.sign(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()));

		response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
	}
}
