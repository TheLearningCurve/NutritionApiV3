package com.kandbnutrition.resource;

/*
 * Create by Kyle Wolff 1/17/2016
 * 
 * CellData is an Enum that will hold the previously selected cell and its id
 * This data is used for the logic in CustomSearchCell
 */

import com.kandbnutrition.controller.CustomSearchCell;

public enum CellData {
	INSTANCE;
	
	public static CellData cellData = null;
	
	private CustomSearchCell previousCell;
	private String resourceId;
	
	public void setSelectedCell(CustomSearchCell cell) {
		 previousCell = cell;
	}
	
	public CustomSearchCell getPreviouslySelectedCell() {
		return previousCell;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

}
