package com.niit.dao;

import java.util.List;

import com.niit.model.Friend;
import com.niit.model.User;

public interface FriendDao
{
	List<User> getListOfSuggestedUsers(String username);

	void addFriendRequest(String username, String toId);

	List<Friend> getPendingRequests(String username);

	void updatePendingRequest(Friend pendingRequest);

	List<Friend> listOfFriends(String username);

	
}
