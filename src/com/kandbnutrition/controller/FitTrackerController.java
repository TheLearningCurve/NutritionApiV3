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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class FitTrackerController extends AnchorPane implements Initializable{
	
	@FXML 
	Button addExerciseButton, sunButton, monButton, tuesButton, wedButton, thursButton, friButton, satButton;
	
	@FXML
	TextField workoutTextField;
	
	@FXML 
	ListView<FitTrackerData> listView;
	
	private StringValues stringValues;
	private ObservableList<FitTrackerData> fitTrackerData;
	private boolean createOneCell;
	
	public FitTrackerController() {
		
		stringValues = new StringValues();
		fitTrackerData = FXCollections.observableArrayList();
		createOneCell = true;
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(stringValues.getFitTrackerViewFxml()));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		
		try {
			fxmlLoader.load();
		}
		catch (IOException e){
			throw new RuntimeException(e);
		}
		
		/*
		 * When the user presses the + Add Exercise button this is the listener 
		 * 
		 * This is just a partial change, more will be needed when we put in Firebase
		 */
		
		addExerciseButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				if(createOneCell) {
				
					// Data to test with
					fitTrackerData.add(new FitTrackerData("", "0", "0", "0", "Sun"));
					
					listView.setItems(fitTrackerData);
									
					listView.setCellFactory(new Callback<ListView<FitTrackerData>, ListCell<FitTrackerData>>() {
						
						@Override
						public ListCell<FitTrackerData> call(ListView<FitTrackerData> param) {
		
								return new CustomFitTrackerCell();
						}
					});
				} 	
			}
		});	
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {}

}
