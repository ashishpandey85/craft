package com.intuit.craft.leaderboard.service;

import java.util.List;

import com.intuit.craft.leaderboard.entity.Player;
import com.intuit.craft.leaderboard.entity.PlayerScores;
import com.intuit.craft.leaderboard.exception.DataStorageException;
import com.intuit.craft.leaderboard.exception.LeaderBoardServiceException;

public interface LeaderBoardService {

	public List<Player> getTopPlayers() throws LeaderBoardServiceException;
	
	public void savePlayerScore(Player player) throws LeaderBoardServiceException;
	
	public List<PlayerScores> findAll() throws DataStorageException;
}
