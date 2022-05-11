package com.intuit.craft.leaderboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.craft.leaderboard.constants.Constants;
import com.intuit.craft.leaderboard.entity.Player;
import com.intuit.craft.leaderboard.exception.PublishDataException;

@Service
public class PublishPlayerScoreServiceImpl implements PublishDataService<Player>{

	@Value("${craft.kafka.player.score.topic.name}")
	String playerScoreTopicName;
	
	@Autowired
	KafkaTemplate<String,Player> kafkaTemplate;
	
	@Override
	public void publishData(Player player) throws PublishDataException{
		
		try
		{
			kafkaTemplate.send(Constants.PLAYER_SCORE_TOPIC,player);
		}
		catch( Exception e)
		{
			throw new PublishDataException(Constants.PUBLISH_DATA_EXCEPTION_MSG);
		}
	}
	
}
