package com.kandbnutrition.controller;

/*
 * Create by Kyle Wolff 1/14/2016
 * 
 * This CustomSearchCell is made to be viewed in the JavaFX listView. 
 */

import java.io.IOException;

import com.kandbnutrition.model.DetailData;
import com.kandbnutrition.resource.CellData;
import com.kandbnutrition.resource.NutritionLabelData;
import com.kandbnutrition.resource.StringValues;
import com.kandbnutrition.service.QueryVariables;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class CustomSearchCell extends ListCell<DetailData> {
		
	@FXML
	Label itemNameLabel, brandNameLabel, caloireLabel;
	
	@FXML
	ImageView thumbNailImageView, nutritionLabelIcon;
	
	@FXML
	HBox hBox;
	
	private StringValues stringValues;
	private CellData cellData;
	private Image thumbNailImage;
	private String resourceId;
	private NutritionLabelData nutritionLabelData;
	
	public CustomSearchCell() {
		
		stringValues = new StringValues();
		nutritionLabelData = new NutritionLabelData();
		cellData = CellData.INSTANCE;
				
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(stringValues.getSingleCellFxml()));
		fxmlLoader.setController(this);
		
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		nutritionLabelIcon.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				if(!resourceId.isEmpty()) {
					
					QueryVariables.setItemId(resourceId);
					nutritionLabelData.getNutritionLabel();
				}
			}
		});
	}
	
	/*
	 * addContent adds the itemName only
	 */
	
	protected void addCellItems(String itemName,String resourceId) {

		itemNameLabel.setText(itemName);
		this.resourceId = resourceId;
	}
	
	protected HBox getHBox() {
		return hBox;
	}
	
	/*
	 * expandCell will make the calls to display the detail view.
	 */
	
	protected void expandCell(CustomSearchCell objectClicked) {
		
		changeCellProperties(objectClicked, hBox.getWidth(), 103, true);
		setElementVisibilty(true, objectClicked);
	}
	
	/* 
	 * resetCell will replace the data so we do not make the list lag and make the calls to reset
	 * the cell back to normal.
	 */
	
	protected void resetCell(CustomSearchCell previouslyClickedCell) {
		
		changeCellProperties(previouslyClickedCell, hBox.getWidth(), 35, false);
		setElementVisibilty(false, previouslyClickedCell);
		
		previouslyClickedCell.brandNameLabel.setText("");
		previouslyClickedCell.caloireLabel.setText("");
		previouslyClickedCell.thumbNailImageView.setImage(null);
	}
	
	/*
	 * Set the cells elements visibility status.
	 */
	
	protected void setElementVisibilty(boolean visible, CustomSearchCell cell) {
		
		cell.thumbNailImageView.setVisible(visible);
		cell.brandNameLabel.setVisible(visible);
		cell.caloireLabel.setVisible(visible);
		cell.nutritionLabelIcon.setVisible(visible);
	}
	
	/* 
	 * Change the properties of the cell i.e. height, width etc... 
	 */
	
	protected void changeCellProperties(CustomSearchCell cell,  double hboxWidth,  double hboxHeight, boolean changeLabelAlign) {
		
		cell.hBox.setPrefWidth(hboxWidth);
		cell.hBox.setPrefHeight(hboxHeight);
			
		cell.thumbNailImageView.setFitWidth(hboxWidth / 4);
		cell.thumbNailImageView.setFitHeight((hboxHeight / 2) + 20);
		
		cell.nutritionLabelIcon.setFitWidth(hboxWidth / 4); 
		cell.nutritionLabelIcon.setFitHeight(hboxHeight - (hboxHeight/2)); 
		
		if(cell.thumbNailImageView.getImage() != null && cell.thumbNailImageView.getImage().getWidth() >= 170 && changeLabelAlign == true) {
			
			cell.thumbNailImageView.setFitWidth(hboxWidth / 5);
			
		} else if(cell.thumbNailImageView.getImage() != null && cell.thumbNailImageView.getImage().getWidth() >= 170 && changeLabelAlign == false){
			
			cell.thumbNailImageView.setFitWidth(hboxWidth / 4);	
		}
		
		if(cell.itemNameLabel.getText().length() >= 28) {
			
			cell.itemNameLabel.setPrefHeight(hboxHeight / 2);
			cell.brandNameLabel.setPrefHeight(hboxHeight / 6);
		}
	}
	
	/* 
	 * Will add the rest of the needed data
	 */
	
	protected void addContenToExpandedCell(String brandName, String calorieName, Float nValue, String thumbNail) {
		
		thumbNailImage = new Image(thumbNail);
		
		brandNameLabel.setText(brandName);	
		caloireLabel.setText(calorieName + " " + nValue.intValue());	
		thumbNailImageView.setImage(thumbNailImage);
	}
	
	/*
	 * (non-Javadoc)
	 * @see javafx.scene.control.Cell#updateItem(java.lang.Object, boolean)
	 * 
	 * updateItem is called when the cell needs data. 
	 * 
	 * We create as many cells needed to fill the listView then when we scroll
	 * the cell is recycled and updateItem is called to add the data needed.
	 */
	
	@Override
	public void updateItem(DetailData detailData, boolean empty) {
		
		super.updateItem(detailData, empty);
		
		if(detailData != null) {
			
			addCellItems(detailData.itemName, detailData.resourceId);
			setGraphic(getHBox());
		}
		
		/*
		 * Handles the mouse clicks on the cell
		 */
		
		this.setOnMouseReleased( new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				CustomSearchCell objectClicked = (CustomSearchCell) event.getSource();
				
				/*
				 * When a cell gets clicked we need to remember which cell was clicked
				 */
				
				if(cellData.getPreviouslySelectedCell() == null) {

					if(objectClicked  != null) {
						
						if(detailData != null) {
						
							addContenToExpandedCell(detailData.brandName, detailData.nutrientName, detailData.nutrientValue, detailData.thumbnail);
							expandCell(objectClicked);
							cellData.setSelectedCell(objectClicked);
							cellData.setResourceId(objectClicked.resourceId);
						}
												
					} else {
						event.consume();
					}
					
				/*
				 * We check to see if the object selected is the same as out previous object.
				 * If it is not then we reset the previous cell so the cell is not expanded and then 
				 * add the content to the new selected cell.
				 */
					
				} else if(cellData.getPreviouslySelectedCell() != objectClicked) {
															
					resetCell(cellData.getPreviouslySelectedCell());
					cellData.setSelectedCell(objectClicked);
					cellData.setResourceId(objectClicked.resourceId);
					addContenToExpandedCell(detailData.brandName, detailData.nutrientName, detailData.nutrientValue, detailData.thumbnail);	
					expandCell(objectClicked);
					
				} else {
					event.consume();
				}
			}
		});
		
		/*
		 * This if listener is to know when the selected cell is off the screen. 
		 * Once the cell is being recycled we need to reset the cell so it does not stay expanded. 
		 * Previously if you had an expanded cell and scrolled the cell got recycled and stayed expanded. 
		 */
		
		if(cellData.getPreviouslySelectedCell() !=null) {
			
			cellData.getPreviouslySelectedCell().visibleProperty().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
										
						if(cellData.getPreviouslySelectedCell().resourceId != cellData.getResourceId()) {
								
								resetCell(cellData.getPreviouslySelectedCell());
						}				
				}
			});
		}
	}
}
