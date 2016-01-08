package com.kandbnutrition.model;

import com.google.gson.annotations.SerializedName;

public class Metric {
	
	@SerializedName("qty")
	public float qty;
	
	@SerializedName("uom")
	public String uom;
}
