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
import javafx.scene.shape.Line;

public class CustomerFitTrackerCell extends ListCell<FitTrackerData> {
	
	@FXML 
	Line leftLine, rightLine;
	
	@FXML
	TextField exerciseTextField,  weightTextField, repsTextField, setsTextField;
	
	private StringValues stringValues;
	
	public CustomerFitTrackerCell() {
		
		stringValues = new StringValues();
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(stringValues.getCustomFitTrackerCellFxml()));
		fxmlLoader.setController(this);
		
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void updateItem(FitTrackerData fitTrackerData, boolean empty) {
		
		super.updateItem(fitTrackerData, empty);
	}
}
