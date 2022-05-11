package com.intuit.craft.leaderboard.service;

import com.intuit.craft.leaderboard.exception.ConsumeDataException;

public interface DataIngressService<T>{
	public void consumeDataFromQueue(T newData) throws ConsumeDataException;
}
