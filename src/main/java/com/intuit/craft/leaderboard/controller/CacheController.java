package com.intuit.craft.leaderboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.intuit.craft.leaderboard.entity.PlayerScores;
import com.intuit.craft.leaderboard.service.CacheService;
import com.intuit.craft.leaderboard.service.LeaderBoardService;

@RestController
@RequestMapping("cache")
public class CacheController {

	@Autowired
	LeaderBoardService leaderBoardService;
	
	@Autowired
	CacheService<PlayerScores> cacheService;
	
	@GetMapping("/initialize/{size}")
	public void initialize(@PathVariable int size)
	{
		try
		{
			List<PlayerScores> playerScores = leaderBoardService.findAll();
			cacheService.initialize(playerScores, size);
		}
		catch (Exception c )
		{
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,c.getMessage());
		}
	}
}
