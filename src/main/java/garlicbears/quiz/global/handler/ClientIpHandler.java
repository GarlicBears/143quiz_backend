package garlicbears.quiz.global.handler;

import jakarta.servlet.http.HttpServletRequest;

public class ClientIpHandler {
	public static String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");

		// proxy 환경일 경우
		if (ip == null || ip.isEmpty()) {
			ip = request.getHeader("Proxy-Client-IP");
		}

		// 웹로직 서버일 경우
		if (ip == null || ip.isEmpty()) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}

		if (ip == null || ip.isEmpty()) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}
}
