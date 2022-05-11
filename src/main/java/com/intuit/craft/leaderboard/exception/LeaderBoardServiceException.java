package com.intuit.craft.leaderboard.exception;

import org.springframework.http.HttpStatus;

public class LeaderBoardServiceException extends Exception{

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	HttpStatus httpStatus;
	public LeaderBoardServiceException(String message )
	{
		super(message);
	}
	
	public LeaderBoardServiceException(String message, HttpStatus status )
	{
		super(message);
		this.httpStatus = status;
	}
}
