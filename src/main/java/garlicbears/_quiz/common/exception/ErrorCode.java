package garlicbears._quiz.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
	INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력 값이 유효하지 않습니다."),
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
	MISSING_REQUIRED_FIELDS(HttpStatus.BAD_REQUEST, "필수 입력값이 누락되었습니다."),
	ILLEGAL_RATING_VALUE(HttpStatus.BAD_REQUEST, "별점 값은 0 과 5 사이여야 합니다."),
	MISSING_REQUEST_BODY_VARIABLE(HttpStatus.BAD_REQUEST, "요청 내용의 필드가 누락되었습니다."),
	HEARTS_COUNT_EXCEEDS(HttpStatus.BAD_REQUEST, "하트 개수가 전체 질문 수를 초과했습니다."),
	BADGE_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "뱃지가 이미 존재합니다."),
	EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
	NICKNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."),
	TOPIC_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 주제입니다."),
	QUESTION_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 문제입니다."),
	ALREADY_RATED(HttpStatus.CONFLICT, "이미 존재하는 문제입니다."),
	UNKNOWN_LOCATION(HttpStatus.NOT_FOUND, "주소가 틀렸습니다."),
	UNKNOWN_GENDER(HttpStatus.NOT_FOUND, "성별이 틀렸습니다."),
	UNKNOWN_TOPIC(HttpStatus.NOT_FOUND, "주제를 찾을 수 없습니다."),
	UNKNOWN_QUESTION(HttpStatus.NOT_FOUND, "문제를 찾을 수 없습니다."),
	UNKNOWN_GAMESESSION(HttpStatus.NOT_FOUND, "게임세션을 찾을 수 없습니다."),
	TOPIC_NOT_FOUND(HttpStatus.NOT_FOUND, "주제를 찾을 수 없습니다."),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
	QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "문제를 찾을 수 없습니다."),
	DELETED_TOPIC(HttpStatus.GONE, "삭제된 주제입니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류입니다."),
	FILE_IO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 처리중 발생한 오류입니다.");

	private final HttpStatus status;
	private final String message;

	ErrorCode(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}
}
