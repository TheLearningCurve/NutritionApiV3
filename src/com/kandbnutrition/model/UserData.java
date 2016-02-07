package com.kandbnutrition.model;


/*
 * Created 1/30/2016 by Kyle Wolff 
 * 
 * This class is a singleton class to store the users data. 
 * 
 * The uID is the main String here because that is what Identifies the user for different calls
 * that will be made for the Fittracker. 
 */

public enum UserData {
	INSTANCE;
	
	private String email, firstName, lastName, uID;
	
	/*
	 * Set all of the users information on login, when Authenticated 
	 */
	
	public void setUserInformation(String email, String firstName, String lastName, String uID) {
		
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.uID = uID;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getuID() {
		return uID;
	}
}
