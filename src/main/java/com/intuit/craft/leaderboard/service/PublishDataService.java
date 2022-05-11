package com.intuit.craft.leaderboard.service;

import com.intuit.craft.leaderboard.entity.Player;
import com.intuit.craft.leaderboard.exception.PublishDataException;

public interface PublishDataService<T> {

	public void publishData(T data) throws PublishDataException;
}
