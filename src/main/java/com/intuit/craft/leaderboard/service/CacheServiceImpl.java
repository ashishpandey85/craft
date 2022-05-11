package com.intuit.craft.leaderboard.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.intuit.craft.leaderboard.constants.Constants;
import com.intuit.craft.leaderboard.entity.PlayerScores;
import com.intuit.craft.leaderboard.exception.CacheInitializationException;
import com.intuit.craft.leaderboard.exception.CacheUpdateFailureException;

@Service
public class CacheServiceImpl implements CacheService<PlayerScores>{

	Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);
	
	int cacheSize;
	PriorityQueue<PlayerScores> minHeap;
	Map<String, PlayerScores> playerScores;
	boolean initialized;
	
	@Value("${craft.leaderboard.max.cache.size}")
	Integer maxCacheSize;
	
	@Override
	public boolean isInitialized() {
		return initialized;
	}
	
	@Override
	public void initialize(List<PlayerScores> data, int size) throws CacheInitializationException {
		// TODO Auto-generated method stub
		try
		{
			if( size > maxCacheSize)
				throw new CacheInitializationException(Constants.CACHE_SIZE_EXCEPTION_MSG);
			if(!initialized )
			{
				this.cacheSize = size;
				this.initialized = true;
				minHeap = new PriorityQueue<PlayerScores>();
				playerScores = new HashMap<String, PlayerScores>();
				for( PlayerScores score: data )
				{
					if( minHeap.size() < cacheSize )
					{
						minHeap.add(score);
						playerScores.put(score.getPlayerId(),score);
					}
					else
					{
						if( score.getHiScore() > minHeap.peek().getHiScore())
						{
							PlayerScores removedScore = minHeap.poll();
							minHeap.add(score);
							playerScores.remove(removedScore.getPlayerId());
							playerScores.put(score.getPlayerId(), score);
						}
					}
				}
			}
		}
		catch ( Exception e ) 
		{
			throw new CacheInitializationException(Constants.CACHE_INITIALIZATION_EXCEPTION_MSG);
		}
		
	}

	@Override
	public void addToCache(PlayerScores score) throws CacheUpdateFailureException{
		try
		{
			if(initialized)
			{
				if( minHeap.size() < cacheSize )
				{
					minHeap.add(score);
					playerScores.put(score.getPlayerId(),score);
				}
				else
				{

					if( score.getHiScore() > minHeap.peek().getHiScore())
					{
						PlayerScores removedScore = minHeap.poll();
						minHeap.add(score);
						playerScores.remove(removedScore.getPlayerId());
						playerScores.put(score.getPlayerId(), score);
					}
				}
			}
		}
		catch( Exception e )
		{
			logger.error(Constants.CACHE_UPDATE_EXCEPTION_MSG + "for player score "+ score.toString());
			throw new CacheUpdateFailureException(Constants.CACHE_UPDATE_EXCEPTION_MSG);
		}

	}

	@Override
	public List<PlayerScores> getTopData() {
		// TODO Auto-generated method stub
		return new ArrayList<PlayerScores>(minHeap);
	}

	@Override
	public PlayerScores getData(String Id) {
		// TODO Auto-generated method stub
		return null;
	}

}
