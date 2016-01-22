package com.kandbnutrition.model;


/*
 * Created by Kyle Wolff 1/21/2016
 * 
 * This will be the data used for the FitTrackerView
 */

public class FitTrackerData {
	
	public String exerciseName,weight, reps, sets, day;
	
	
	public FitTrackerData(String exerciseName, String weight, String reps, String sets, String day ) {
		
		this.exerciseName = exerciseName;
		this.weight = weight;
		this.reps = reps;
		this.sets = sets;
		this.day = day;
	}

}
