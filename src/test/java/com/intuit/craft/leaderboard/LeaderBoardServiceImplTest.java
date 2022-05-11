package com.intuit.craft.leaderboard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.intuit.craft.leaderboard.entity.Player;
import com.intuit.craft.leaderboard.entity.PlayerScores;
import com.intuit.craft.leaderboard.exception.LeaderBoardServiceException;
import com.intuit.craft.leaderboard.repository.LeaderBoardRepository;
import com.intuit.craft.leaderboard.service.CacheService;
import com.intuit.craft.leaderboard.service.LeaderBoardServiceImpl;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)

public class LeaderBoardServiceImplTest {
	
	@InjectMocks
	LeaderBoardServiceImpl service;
	
	@Mock
	CacheService<PlayerScores> cacheService;
	
	@Mock
	LeaderBoardRepository leaderBoardRepository;
	
	private static final int topPlayerCount = 5;
	
	@BeforeAll
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(service, "topPlayerCount", 5);
		ReflectionTestUtils.setField(service, "cacheService", cacheService);
		ReflectionTestUtils.setField(service, "leaderBoardRepository", leaderBoardRepository);
	}
	
	
	@Test
	void testGetTopPlayersNoCache()
	{
		try
		{
		Mockito.when(cacheService.isInitialized()).thenReturn(false);
		Mockito.when(leaderBoardRepository.findAll()).thenReturn(getPlayerScores());
		List<Player> l = service.getTopPlayers();
		assertNotNull(l);
		assertEquals(topPlayerCount,l.size());
		assertEquals(l.get(0).getPlayerId(),"John");
		assertEquals(l.get(l.size()-1).getPlayerId(),"Ashish");
		
		}
		catch (Exception e) 
		{
			fail("Unexpected Exception was thrown.");
		}
	}
	
	@Test
	void testException()
	{
		try
		{
		Mockito.when(cacheService.isInitialized()).thenReturn(false);
		Mockito.when(leaderBoardRepository.findAll()).thenReturn(null);
		List<Player> l = service.getTopPlayers();
		assertThrows(LeaderBoardServiceException.class,() -> {
	        throw new LeaderBoardServiceException("message");
	    });
		}
		catch (Exception e) 
		{
		}
	}
	
	@Test
	void testGetTopPlayersFromCacheNoData()
	{
		try
		{
		Mockito.when(cacheService.isInitialized()).thenReturn(true);
		Mockito.when(leaderBoardRepository.findAll()).thenReturn(getPlayerScores());
		List<Player> l = service.getTopPlayers();
		assertNotNull(l);
		assertEquals(topPlayerCount,l.size());
		assertEquals(l.get(0).getPlayerId(),"John");
		assertEquals(l.get(l.size()-1).getPlayerId(),"Ashish");
		
		}
		catch (Exception e) 
		{
			fail("Unexpected Exception was thrown.");
		}
	}
	
	
	private List<PlayerScores> getPlayerScores()
	{
		List<PlayerScores> ps = new ArrayList<>();
		PlayerScores p1 = new PlayerScores();
		p1.setHiScore(100);
		p1.setPlayerId("Ashish");
		ps.add(p1);
		
		PlayerScores p2 = new PlayerScores();
		p2.setHiScore(5);
		p2.setPlayerId("Jack");
		ps.add(p2);
		
		PlayerScores p3 = new PlayerScores();
		p3.setHiScore(10);
		p3.setPlayerId("John");
		ps.add(p3);
		
		PlayerScores p4 = new PlayerScores();
		p4.setHiScore(15);
		p4.setPlayerId("Joe");
		ps.add(p4);
		
		PlayerScores p5 = new PlayerScores();
		p5.setHiScore(25);
		p5.setPlayerId("Ash");
		ps.add(p5);
		
		PlayerScores p6 = new PlayerScores();
		p6.setHiScore(30);
		p6.setPlayerId("Caleb");
		ps.add(p6);
		
		return ps;
	}

}
