package com.kandbnutrition.resource;

public class Style {
	
	private String fxCSS = "-fx-";
	private String color;
	
	public void setTextFill(String color) {
		this.color = fxCSS + "text-fill:" + color;
	}
	
	public String getTextFill() {
		return color;
	}

}
