package com.kandbnutrition.model;
import com.google.gson.annotations.SerializedName;

/*
 * Created by Kyle Wolff May 8 2015
 */
public class Results {

	 @SerializedName("item_name")
	 public String itemName;
	 
	 @SerializedName("brand_name")
	 public String brandName;
	 
	 @SerializedName("nutrient_name")
	 public String nutruentName;
	 
	 @SerializedName("nutrient_value")
	 public float nutrientValue;
	 
	 @SerializedName("nutrient_uom")
	 public String nutrientUom;
	 
	 @SerializedName("serving_qty")
	 public float servingQty;
	 
	 @SerializedName("serving_uom")
	 public String servingUom;
	 
	 @SerializedName("resource_id")
	 public String resourceId;
	 
	 @SerializedName("thumbnail")
	 public String thumbnail;
	 
}
