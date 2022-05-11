package com.intuit.craft.leaderboard.service;

import java.util.List;

import com.intuit.craft.leaderboard.exception.CacheInitializationException;
import com.intuit.craft.leaderboard.exception.CacheUpdateFailureException;

public interface CacheService<T> {

	public void initialize(List<T> data , int size) throws CacheInitializationException;
	public void addToCache(T data) throws CacheUpdateFailureException;
	public List<T> getTopData();
	public T getData(String Id);
	public boolean isInitialized();
}
