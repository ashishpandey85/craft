package com.intuit.craft.leaderboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.intuit.craft.leaderboard.constants.Constants;
import com.intuit.craft.leaderboard.entity.Player;
import com.intuit.craft.leaderboard.exception.ConsumeDataException;
import com.intuit.craft.leaderboard.service.LeaderBoardService;

@Service
public class PlayerScoreConsumerImpl implements DataIngressService<Player>{
	

	@Autowired
	LeaderBoardService leaderBoardService;
	
	@KafkaListener(topics = Constants.PLAYER_SCORE_TOPIC, groupId = "scoreGroupId")
	public void consumeDataFromQueue(Player newData) throws ConsumeDataException{
		try {
			leaderBoardService.savePlayerScore(newData);
		} catch (Exception e) {
			throw new ConsumeDataException(Constants.CONSUME_DATA_EXCEPTION_MSG);
		}
	}

}
