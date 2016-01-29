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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class FitTrackerController extends AnchorPane implements Initializable{
	
	@FXML 
	Button addExerciseButton, deleteButton, sunButton, monButton, tuesButton, wedButton, thursButton, friButton, satButton;
	
	@FXML
	TextField workoutTextField;
	
	@FXML 
	ListView<FitTrackerData> listView;
	
	private FitTrackerController controller;
	private StringValues stringValues;
	private ObservableList<FitTrackerData> fitTrackerData;
	private ObservableList<FitTrackerData> fitTrackerDataCopy;
	private boolean createOneCell, delete;
	private int index, clickedAmount;
	
	public FitTrackerController() {
		
		stringValues = new StringValues();
		fitTrackerData = FXCollections.observableArrayList();
		createOneCell = true;
		clickedAmount = 1;
				
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(stringValues.getFitTrackerViewFxml()));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		
		try {
			fxmlLoader.load();
		}
		catch (IOException e){
			throw new RuntimeException(e);
		}
		
		controller = this;
		
		/*
		 * When the user presses the + Add Exercise button this is the listener 
		 * 
		 * This is just a partial change, more will be needed when we put in Firebase
		 */
		
		addExerciseButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
					// Data to test with
					fitTrackerData.add(new FitTrackerData("", "0", "0", "0", "Sun"));
					
					listView.setItems(fitTrackerData);
					setCellFactory();	
			}
		});	
		
		/*
		 * This listener determines when the user is in a state to delete items
		 */
		
		deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				delete = true;
								
				if(clickedAmount == 1) {
					
					deleteButton.setStyle("-fx-text-fill: red;");
					clickedAmount = 2;
				} else if(clickedAmount == 2) {
					
					clickedAmount = 1;
					delete = false;
					deleteButton.setStyle("-fx-text-fill: #212121;");
				}
			}
		});
	}
	
	/*
	 * Needed to put the setCellFactory as a method to use multiple times
	 */

	public void setCellFactory() {
		
		listView.setCellFactory(new Callback<ListView<FitTrackerData>, ListCell<FitTrackerData>>() {
			
			@Override
			public ListCell<FitTrackerData> call(ListView<FitTrackerData> param) {
				
				return new CustomFitTrackerCell(controller);
			}
		});	
	}
	
	public boolean getDelete() {
		return delete;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public void setCopyOfList(ObservableList<FitTrackerData> fitTrackerDataCopy) {
		this.fitTrackerDataCopy = fitTrackerDataCopy;
		
		listView.setItems(this.fitTrackerDataCopy);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {}

}
