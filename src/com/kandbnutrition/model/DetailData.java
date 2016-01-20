package com.kandbnutrition.model;


public class DetailData {
	
	 public String itemName, brandName, nutrientName , nutrientUom, servingUom, resourceId, thumbnail;
	 
	 public float nutrientValue, servingQty;
	 
	 public DetailData(String itemName, String brandName, String nutrientName, float nValue, String nUoM, float sQty, String sUoM, 
			 String id, String thumbI) {
		 
		 this.itemName = itemName;
		 
		 this.brandName = brandName;
		 
		 this.nutrientName = nutrientName;
		 
		 this.nutrientValue = nValue;
		 
		 this.nutrientUom = nUoM;
		 
		 this.servingQty = sQty;
		 
		 this.servingUom = sUoM;
		 
		 this.resourceId = id;
		 
		 this.thumbnail = thumbI;
		 
	 }

	public String getItemName() {
		return itemName;
	}

	public String getBrandName() {
		return brandName;
	}

	public String getNutrientName() {
		return nutrientName;
	}

	public String getNutrientUom() {
		return nutrientUom;
	}

	public String getServingUom() {
		return servingUom;
	}

	public String getResourceId() {
		return resourceId;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public float getNutrientValue() {
		return nutrientValue;
	}

	public float getServingQty() {
		return servingQty;
	}
	 
	 
}
