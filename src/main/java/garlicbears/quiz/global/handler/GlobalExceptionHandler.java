package garlicbears.quiz.global.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import garlicbears.quiz.domain.common.dto.ResponseDto;
import garlicbears.quiz.global.exception.CustomException;
import garlicbears.quiz.global.exception.ErrorCode;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * CustomException을 처리하는 핸들러
	 */
	@ExceptionHandler
	public ResponseEntity<ResponseDto<String>> handleCustomException(CustomException e) {
		ErrorCode errorCode = e.getErrorCode();
		ResponseDto<String> response = new ResponseDto<>(errorCode.getStatus().value(),
			errorCode.toString());
		return new ResponseEntity<>(response, errorCode.getStatus());
	}

	/**
	 * NullPointerException을 처리하는 핸들러
	 */
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ResponseDto<String>> handleNullPointerException(NullPointerException e) {
		ErrorCode errorCode = ErrorCode.MISSING_REQUEST_BODY_VARIABLE;

		// 예외 스택 트레이스를 로깅합니다.
		logger.error("NullPointerException occurred: ", e);

		ResponseDto<String> response = new ResponseDto<>(errorCode.getStatus().value(), errorCode.toString());
		return new ResponseEntity<>(response, errorCode.getStatus());
	}
}
