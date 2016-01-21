package com.kandbnutrition.controller;

/*
 * Created by Kyle Wolff 1/21/2016
 * 
 * This is the controller for the FitTrackerView
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.kandbnutrition.model.FitTrackerData;
import com.kandbnutrition.resource.StringValues;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class FitTrackerController extends AnchorPane implements Initializable{
	
	@FXML 
	Button addExerciseButton, sunButton, monButton, tuesButton, wedButton, thursButton, friButton, satButton;
	
	@FXML
	TextField workoutTextField;
	
	@FXML 
	ListView<FitTrackerData> listView;
	
	private StringValues stringValues;
	
	public FitTrackerController() {
		
		stringValues = new StringValues();
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(stringValues.getFitTrackerViewFxml()));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		
		try {
			fxmlLoader.load();
		}
		catch (IOException e){
			throw new RuntimeException(e);
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {}

}
