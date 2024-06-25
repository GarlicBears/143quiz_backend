package garlicbears._quiz.game.user.repository;

import java.util.List;

import garlicbears._quiz.game.user.dto.TopicsListDto;

public interface GameUserTopicQuery {

	//뱃지 미획득 주제 목록
	public List<TopicsListDto> findUnacquaintedBadgeTopicsByUser(long userId);

	//뱃지 획득 주제 목록
	public List<TopicsListDto> findTopicsWithBadgeByUser(long userId);

}
