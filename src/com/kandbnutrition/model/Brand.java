package com.kandbnutrition.model;
import com.google.gson.annotations.SerializedName;

/*
 * Create by Kyle Wolff May 8 2015
 */
public class Brand {
	
	@SerializedName("name")
	public String brandName;
	
	@SerializedName("website")
	public String websiteUrl;
	
	@SerializedName("logo")
	public String imageLogo;
}
