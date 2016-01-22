package com.kandbnutrition.controller;

/*
 * Created by Kyle Wolff 1/21/2016
 * 
 * This is the controller for the custom cell used in the FitTrackerView
 */

import java.io.IOException;

import com.kandbnutrition.model.FitTrackerData;
import com.kandbnutrition.resource.StringValues;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

public class CustomFitTrackerCell extends ListCell<FitTrackerData> {
	
	@FXML
	AnchorPane anchorPane;
	
	@FXML 
	Line leftLine, rightLine;
	
	@FXML
	TextField exerciseTextField,  weightTextField, repsTextField, setsTextField;
	
	private StringValues stringValues;
	private String day;
	
	public CustomFitTrackerCell() {
		
		stringValues = new StringValues();
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(stringValues.getCustomFitTrackerCellFxml()));
		fxmlLoader.setController(this);
		
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/*
	 * Graphic needed for display
	 */
	
	public AnchorPane getAnchorPane() {
		return anchorPane;
	}
	
	/*
	 * Data to display 
	 */
	
	public void addContent(String exerciseName, String weight, String reps, String sets, String day) {
		
		exerciseTextField.setText(exerciseName);
		weightTextField.setText(weight);
		repsTextField.setText(reps);
		setsTextField.setText(sets);
		this.day = day;
	}
	
	/*
	 * (non-Javadoc)
	 * @see javafx.scene.control.Cell#updateItem(java.lang.Object, boolean)
	 * 
	 * This method will be called on creating a cell and when we recycle the cells
	 */
	
	@Override
	public void updateItem(FitTrackerData fitTrackerData, boolean empty) {
		
		super.updateItem(fitTrackerData, empty);
		
		if(fitTrackerData != null) {
			
			addContent(fitTrackerData.exerciseName, fitTrackerData.weight, fitTrackerData.reps, fitTrackerData.sets, fitTrackerData.day);
			setGraphic(getAnchorPane());
		}
	}
}
