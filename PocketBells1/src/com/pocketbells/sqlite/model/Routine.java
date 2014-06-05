package com.pocketbells.sqlite.model;

public class Routine {
	int userId;
	String userRoutine;

	//constructors
	public Routine() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public Routine(int userId, String userRoutine) {
		super();
		this.userId = userId;
		this.userRoutine = userRoutine;
	}



	//getters and setters
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserRoutine() {
		return userRoutine;
	}
	public void setUserRoutine(String userRoutine) {
		this.userRoutine = userRoutine;
	}


}
