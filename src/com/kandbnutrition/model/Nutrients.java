package com.kandbnutrition.model;

import com.google.gson.annotations.SerializedName;

public class Nutrients {

	@SerializedName("attr_id")
	public int attr_id;
	
	@SerializedName("value")
	public float value;

	@SerializedName("unit")
	public String unit;

	@SerializedName("name")
	public String name;

	@SerializedName("usda_tag")
	public String usda_tag;
}

