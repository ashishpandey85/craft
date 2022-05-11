package com.intuit.craft.leaderboard.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.intuit.craft.leaderboard.entity.Player;
import com.intuit.craft.leaderboard.service.PublishDataService;

@RestController
@RequestMapping("kafka")
public class KafkaController {

	@Autowired
	PublishDataService<Player> publishPlayerScoreService;
	
	@PostMapping("/player")
	public ResponseEntity<String> publishToKafka(@RequestBody Player player)
	{
		try
		{
			publishPlayerScoreService.publishData(player);
			return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
		}
		catch (Exception e )
		{
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
		}

	}
	
}
