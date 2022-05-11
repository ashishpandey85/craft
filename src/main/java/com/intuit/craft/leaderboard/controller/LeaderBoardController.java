package com.intuit.craft.leaderboard.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.intuit.craft.leaderboard.entity.Player;
import com.intuit.craft.leaderboard.exception.LeaderBoardServiceException;
import com.intuit.craft.leaderboard.service.LeaderBoardService;

@RestController
@RequestMapping("leaderboard")
public class LeaderBoardController {

	Logger logger = LoggerFactory.getLogger(LeaderBoardController.class);
	@Autowired
	LeaderBoardService leaderBoardService;
	
	@GetMapping("/topPlayers")
	public List<Player> retrieveTopPlayers()
	{
		try
		{
			return leaderBoardService.getTopPlayers();
		}
		catch (LeaderBoardServiceException l )
		{
			logger.error(l.getMessage());
			throw new ResponseStatusException(l.getHttpStatus() == null 
					? HttpStatus.INTERNAL_SERVER_ERROR : l.getHttpStatus(),l.getMessage());
		}
	}
	
	
	
}
