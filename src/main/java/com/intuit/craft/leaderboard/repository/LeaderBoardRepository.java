package com.intuit.craft.leaderboard.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.intuit.craft.leaderboard.entity.PlayerScores;

public interface LeaderBoardRepository extends MongoRepository<PlayerScores, String> {

//	public List<PlayerScores> retrieveTopPlayers();
//	public void savePlayerScore(PlayerScores player);
//	public PlayerScores retrievePlayerScore(String playerId);
}
