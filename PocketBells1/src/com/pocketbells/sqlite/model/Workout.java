package com.pocketbells.sqlite.model;

public class Workout {
	int workoutId;
	int userWorkId;
	double weight;
    double cals;
	String workoutDate;
	
	// constructors
	
	public Workout() {
		// TODO Auto-generated constructor stub
	}
	
    public Workout( int userWorkId, double weight, double cals,
			String workoutDate) {
		super();
		//this.workoutId = workoutId;
		this.userWorkId = userWorkId;
		this.weight = weight;
		this.cals = cals;
		this.workoutDate = workoutDate;
	}

	//getters and setters
	public int getWorkoutId() {
		return workoutId;
	}
	public void setWorkoutId(int workoutId) {
		this.workoutId = workoutId;
	}
	public int getUserWorkId() {
		return userWorkId;
	}
	public void setUserWorkId(int userWorkId) {
		this.userWorkId = userWorkId;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getCals() {
		return cals;
	}
	public void setCals(double cals) {
		this.cals = cals;
	}
	public String getWorkoutDate() {
		return workoutDate;
	}
	public void setWorkoutDate(String workoutDate) {
		this.workoutDate = workoutDate;
	}
	

}
