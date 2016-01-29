package com.kandbnutrition.controller;

/*
 * Created by Kyle Wolff 1/21/2016
 * 
 * This is the controller for the custom cell used in the FitTrackerView
 */

import java.io.IOException;

import com.kandbnutrition.model.FitTrackerData;
import com.kandbnutrition.resource.StringValues;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

public class CustomFitTrackerCell extends ListCell<FitTrackerData> {
	
	@FXML
	AnchorPane anchorPane;
	
	@FXML 
	Line leftLine, rightLine;
	
	@FXML
	Label weightLabel, setsLabel, repsLabel;
	
	@FXML
	TextField exerciseTextField,  weightTextField, repsTextField, setsTextField;
	
	private StringValues stringValues;
	private FitTrackerController controller;
	private String day;
	
	public CustomFitTrackerCell(FitTrackerController controller) {
		
		stringValues = new StringValues();
		this.controller = controller;
				
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(stringValues.getCustomFitTrackerCellFxml()));
		fxmlLoader.setController(this);
		
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		/*
		 * This hover listener changes the style of the cell depending on if the user is in a delete state
		 */
		
		this.hoverProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				
				if(controller.getDelete()) {
																				
					if(newValue == true) {
						anchorPane.setStyle("-fx-background-color:red;");
						weightLabel.setStyle("-fx-text-fill: white;");
						repsLabel.setStyle("-fx-text-fill: white;");
						setsLabel.setStyle("-fx-text-fill: white;");
						exerciseTextField.setStyle("-fx-text-fill: white;");
					} else {
						anchorPane.setStyle("-fx-background-color:white;");
						weightLabel.setStyle("-fx-text-fill: #727272;");
						repsLabel.setStyle("-fx-text-fill: #727272;");
						setsLabel.setStyle("-fx-text-fill: #727272;");
						exerciseTextField.setStyle("-fx-text-fill: #212121;");
					}
				}
			}
		});
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
		
		/*
		 * If the user is a delete state this mouse listener will delete the items
		 * and refresh the listView 
		 */
		
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				if(controller.getDelete()) {
					controller.listView.getItems().remove(getIndex());
					controller.setCopyOfList(controller.listView.getItems());
					controller.setCellFactory();
				}
			}
		});
	}
}
