package com.intuit.craft.leaderboard.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.intuit.craft.leaderboard.constants.Constants;
import com.intuit.craft.leaderboard.entity.Player;
import com.intuit.craft.leaderboard.entity.PlayerScores;
import com.intuit.craft.leaderboard.exception.DataStorageException;
import com.intuit.craft.leaderboard.exception.LeaderBoardServiceException;
import com.intuit.craft.leaderboard.repository.LeaderBoardRepository;

@Service
public class LeaderBoardServiceImpl implements LeaderBoardService{
	
	Logger logger = LoggerFactory.getLogger(LeaderBoardServiceImpl.class);

	@Autowired
	LeaderBoardRepository leaderBoardRepository;
	
	@Value("${craft.leaderboard.top.player.count}")
	int topPlayerCount;
	
	@Autowired
	CacheService<PlayerScores> cacheService;
	
	@Override
	public List<Player> getTopPlayers() throws LeaderBoardServiceException{
		// TODO Auto-generated method stub
		//		List<PlayerScores> players = repository.findAll(Sort.by(Sort.Direction.ASC, "hiScore"));
		try
		{
			List<PlayerScores> players = null;
			if(cacheService.isInitialized())
			{
				players = cacheService.getTopData();
			}
			if(null == players || players.isEmpty())
				players = findAll();
			if( null == players || players.isEmpty())
			{
				logger.error(Constants.LEADERBOARD_SRVC_NO_DATA_FOUND_EXCEPTION_MSG);
				throw new LeaderBoardServiceException(Constants.LEADERBOARD_SRVC_NO_DATA_FOUND_EXCEPTION_MSG,HttpStatus.NOT_FOUND);
			}
			Collections.sort(players);
			return getHiScoreList(players);
		}
		catch( DataStorageException d )
		{
			throw new LeaderBoardServiceException(d.getMessage());
		}
		catch (Exception e)
		{
			throw new LeaderBoardServiceException(Constants.LEADERBOARD_SRVC_TOP_PLAYERS_EXCEPTION_MSG);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void savePlayerScore(Player player) throws LeaderBoardServiceException{
		try
		{
			Optional<PlayerScores> playerScore = leaderBoardRepository.findById(player.getPlayerId());
			if(!playerScore.isPresent())
			{
				playerScore = Optional.of(new PlayerScores());
				playerScore.get().setScores(new ArrayList<Integer>());
				playerScore.get().setPlayerId(player.getPlayerId());
			}
			if( playerScore.get().getHiScore() < player.getScore())
			{
				playerScore.get().setHiScore(player.getScore());
			}
			playerScore.get().getScores().add(player.getScore());
			leaderBoardRepository.save(playerScore.get());
			cacheService.addToCache(playerScore.get());
		}
		catch( Exception e )
		{
			logger.error(Constants.LEADERBOARD_SRVC_SAVE_DATA_EXCEPTION_MSG);
			throw new LeaderBoardServiceException(Constants.LEADERBOARD_SRVC_SAVE_DATA_EXCEPTION_MSG);
		}
	}
	
	@Override
	public List<PlayerScores> findAll() throws DataStorageException{
		try
		{
			return leaderBoardRepository.findAll();
		}
		catch (Exception e )
		{
			logger.error(Constants.DATA_RETRIEVAL_EXCEPTION_MSG);
			throw new DataStorageException(Constants.DATA_RETRIEVAL_EXCEPTION_MSG);
		}
		
	}
	
	private List<Player> getHiScoreList(List<PlayerScores> playerScores)
	{
		if( playerScores.size() > topPlayerCount )
		{
			playerScores = playerScores.subList(playerScores.size()-topPlayerCount, playerScores.size());
		}
		List<Player> players = new ArrayList<>();
		playerScores.stream().forEach(p->{
			Player p1 = new Player();
			p1.setPlayerId(p.getPlayerId());
			p1.setScore(p.getHiScore());
			players.add(p1);
		});
			
		return players;
	}

}
