package com.pocketbells.sqlite.model;

public class User {
	int id;
    String username;
    String eMail;
	String password;
	double weight;
    double targetWeight;
	String start_date;
 
    // constructors
    public User() {
    }
 
    public void setId(int id) {
		this.id = id;
	}

	public User(String uName, double uWeight) {
        this.username = uName;
        this.weight = uWeight;
    }
 
    public User(int uId, String uName, double uWeight) {
        this.id = uId;
        this.username = uName;
        this.weight = uWeight;
    }
	public User(int id, String username, String eMail, String password,
			double weight, double targetWeight, String start_date) {
		super();
		this.id = id;
		this.username = username;
		this.eMail = eMail;
		this.password = password;
		this.weight = weight;
		this.targetWeight = targetWeight;
		this.start_date = start_date;
	}
 
    // setters
   // public void setId(int uId) {
      //  this.id = uId;
   // }
 
    public void setUserName(String uName) {
        this.username = uName;
    }

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
 
    public void setWeight(double uWeight) {
        this.weight = uWeight;
    }
     
    public void setStartDate(String created_at){
        this.start_date = created_at;
    }

	public void setEmail(String eMail) {
		this.eMail = eMail;
	}
 
    // getters
    public long getId() {
        return this.id;
    }
 
    public String getUserName() {
        return this.username;
    }
    public String getEmail() {
 		return eMail;
 	}
 
    public double getWeight() {
        return this.weight;
    }
    public double getTargetWeight() {
		return targetWeight;
	}

	public void setTargetWeight(double targetWeight) {
		this.targetWeight = targetWeight;
	}
    public String getStart_date() {
		return start_date;
	}


	
}



