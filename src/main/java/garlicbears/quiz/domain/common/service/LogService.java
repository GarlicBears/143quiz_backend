package garlicbears.quiz.domain.common.service;

import org.springframework.stereotype.Service;

import garlicbears.quiz.domain.common.entity.User;
import garlicbears.quiz.domain.common.entity.Log;
import garlicbears.quiz.domain.common.repository.LogRepository;

@Service
public class LogService {
	private final LogRepository logRepository;

	public LogService(LogRepository logRepository) {
		this.logRepository = logRepository;
	}

	public void log(User user, String url, String ip) {
		logRepository.save(new Log(user, url, ip));
	}
}
