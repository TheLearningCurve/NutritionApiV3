package com.kandbnutrition.model;
import com.google.gson.annotations.SerializedName;

/*
 * Created by Kyle Wolff May 8 2015
 */
public class Serving {

	@SerializedName("qty")
	public float quantity;
	
	@SerializedName("uom")
	public String uom;
	
	@SerializedName("per_container")
	public int perContainer;
	
	@SerializedName("metric")
	public Metric metric;
}
