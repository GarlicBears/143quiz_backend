package garlicbears._quiz.game.user.dto;

import java.util.List;

/**
 * 게임 주제 목록 ( 뱃지 미획득 / 뱃지 획득 )
 */

public class ResponseTopicBadgeDto {

	private List<TopicsListDto> topics;

	public ResponseTopicBadgeDto() {
	}

	public ResponseTopicBadgeDto(List<TopicsListDto> topics) {
		this.topics = topics;
	}

	public List<TopicsListDto> getTopics() {
		return topics;
	}

}
