package garlicbears.quiz.global.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import garlicbears.quiz.domain.common.dto.ResponseDto;
import garlicbears.quiz.global.exception.CustomException;
import garlicbears.quiz.global.exception.ErrorCode;

@ControllerAdvice
public class GlobalExceptionHandler {

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
		ResponseDto<String> response = new ResponseDto<>(errorCode.getStatus().value(), errorCode.toString());
		return new ResponseEntity<>(response, errorCode.getStatus());
	}
}