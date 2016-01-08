package com.kandbnutrition.resource;

public class StringValues {
	
	private String VIEWPATH 		 = "/com/kandbnutrition/views/";
	private String MAINFRAMEFXML	 = VIEWPATH + "MainFrame.fxml";
	private String SEARCHFIELDFXML	 = VIEWPATH + "SearchFieldFrame.fxml";
	private String SEARCHLISTFXML 	 = VIEWPATH + "SearchListFrame.fxml";
	private String EXPANDEDLABELFXML = VIEWPATH + "ExpandableTextCell.fxml";
	private String NUTRITIONLABELFXML= VIEWPATH + "NutritionLabelFrame.fxml";
	private String SINGLECELLFXML    = VIEWPATH + "SingleTextCell.fxml";

	
	public String getMainFrameFxml(){
		return MAINFRAMEFXML;
	}
	
	public String getSearchFieldFxml(){
		return SEARCHFIELDFXML;
	}
	
	public String getSearchListFxml(){
		return SEARCHLISTFXML;
	}
	
	public String getExpandedLabelFxml(){
		return EXPANDEDLABELFXML;
	}
	
	public String getNutritionLabelFxml(){
		return NUTRITIONLABELFXML;
	}
	
	public String getSingleCellFxml(){
		return SINGLECELLFXML;
	}

}
