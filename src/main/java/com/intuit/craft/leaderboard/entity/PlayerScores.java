package com.intuit.craft.leaderboard.entity;


import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "Player")
public class PlayerScores implements Comparable<PlayerScores>{

	@Id
	private String playerId;
	private int hiScore;
	private List<Integer> scores;
	
	@Override
	public boolean equals(Object p)
	{
		return this.playerId.equals(((PlayerScores)p).playerId)
				&& this.hiScore == ((PlayerScores)p).hiScore;
	}
	
	@Override 
	public int compareTo(PlayerScores p)
	{
		if( this.hiScore == p.hiScore )
			return p.playerId.compareTo(this.playerId);
		return this.hiScore - p.hiScore;
	}
	
	@Override
	public String toString() {
		return "{" + playerId + " " + hiScore + "}";
	}
	
}
