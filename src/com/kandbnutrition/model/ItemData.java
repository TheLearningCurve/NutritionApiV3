package com.kandbnutrition.model;

import com.google.gson.annotations.SerializedName;

/*
 * Create by Kyle Wolff May 8 2015
 */
public class ItemData {
	
	 @SerializedName("name")
	 public String name;
	 
	 @SerializedName("type")
	 public int type;
	 
	 @SerializedName("ingredient_statement")
	 public String ingredientStatement; // may be null
	 
	 @SerializedName("brand")
	 public Brand brand;
	 
	 @SerializedName("images")
	 public Images images;
	 
	 @SerializedName("label")
	 public Label label;
	 

	 	 

}
