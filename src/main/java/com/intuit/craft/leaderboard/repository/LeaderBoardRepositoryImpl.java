//package com.intuit.craft.leaderboard.repository;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.stereotype.Repository;
//
//import com.intuit.craft.leaderboard.entity.PlayerScores;
//
//@Repository
//public abstract class LeaderBoardRepositoryImpl implements LeaderBoardRepository {
//
//	@Autowired
//	MongoTemplate mongoTemplate;
//	
//	@Override
//	public List<PlayerScores> retrieveTopPlayers() {
//		// TODO Auto-generated method stub
////		List<PlayerScores> players = mongoTemplate.findAll(PlayerScores.class);
//		List<PlayerScores> players = findAll(Sort.by(Sort.Direction.DESC, "hiScore"));
//		return players;
//	}
//
//	@Override
//	public void savePlayerScore(PlayerScores player) {
//		mongoTemplate.save(player, "Player");
//	}
//	
//	public PlayerScores retrievePlayerScore(String playerId)
//	{
////		PlayerScores player = mongoTemplate.findById(playerId, PlayerScores.class);
//		PlayerScores player = mongoTemplate.findOne(
//				  Query.query(Criteria.where("playerId").is(playerId)), PlayerScores.class);
//		return player;
//	}
//
//
//}
