package com.kandbnutrition.controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.kandbnutrition.animation.DoubleTransition;
import com.kandbnutrition.handler.FxmlHandler;
import com.kandbnutrition.resource.FeatureState;
import com.kandbnutrition.resource.StringValues;

import javafx.animation.FadeTransition;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;



public class MainFrameController extends AnchorPane implements Initializable, FxmlHandler {		
	
	@FXML
	Pane dimPane, mainFrameTopContainer, mainRightAnchorPane, mainPaneContainer,
		optionsAnchorPane,nutritionLabelDimPane,nutritionLabelContainer;
	@FXML
	SplitPane navMenuSplitPane;
	
	@FXML
	AnchorPane navMenu;
	
	@FXML
	ProgressIndicator ItemListProgressIndicator;
	
	@FXML
	ImageView largeLogo;
	
	@FXML
	Button navMenuButton;
	
	@FXML
	HBox searchIconHBox, macroCalculatorHBox, fitTrackerHBox, logoutHBox, itemListContainer;
	
	private StringValues stringValues;
	private SearchFieldController searchFieldController;
	private FitTrackerController fitTrackerController;
	public SearchListFrameController searchListFrameController;	
	public NutritionLabelFrameController nutritionLabelFrameController;
	
	private boolean open = true;
	private FeatureState featureState = FeatureState.Search;
		
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle){}
	
	public MainFrameController() {
		
		fitTrackerController = new FitTrackerController();
		nutritionLabelFrameController = new NutritionLabelFrameController(this);
		searchListFrameController = new SearchListFrameController(nutritionLabelFrameController, this);
		searchFieldController = new SearchFieldController(searchListFrameController, this);
		stringValues = new StringValues();
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(stringValues.getMainFrameFxml()));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		
		try {
			fxmlLoader.load();
			addFxml();
		}
		catch (IOException e){
			throw new RuntimeException(e);
		}
				
		navMenuButton.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
									
				if(open == true){
					openMenu();
				}else if(open == false){
					closeMenu();
				}
			}
		});
		
		searchIconHBox.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				if(!featureState.equals(FeatureState.Search)) {
					removeFxml();
					featureState = FeatureState.Search;
					addFxml();
				}
				
				closeMenu();
			}
		});
		
		fitTrackerHBox.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				if(!featureState.equals(FeatureState.FitTracker)) {
					removeFxml();
					featureState = FeatureState.FitTracker;
					addFxml();
				}
				
				closeMenu();
			}
		});
		
		macroCalculatorHBox.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				if(!featureState.equals(FeatureState.Macro)) {
					removeFxml();
					featureState = FeatureState.Macro;
					addFxml();
				}
				
				closeMenu();
			}
		});
		
		logoutHBox.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				closeMenu();
			}
		});
	}
	
	public void openMenu() {				
		DoubleProperty doubleProperty = navMenuSplitPane.getDividers().get(0).positionProperty();
		DoubleTransition dt = new DoubleTransition(Duration.millis(1000), doubleProperty);
		dt.setToValue(0.19); dt.play();	
		
		FadeTransition ft = new FadeTransition(Duration.millis(1000), dimPane);
		ft.setFromValue(0.0);
		ft.setToValue(.45);
		ft.play();		
		
		ft.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				doubleProperty.getValue();
				dimPane.setMouseTransparent(false);
				open = false;
			}
		});	
	}
	
	public void closeMenu() {			
		DoubleProperty doubleProperty = navMenuSplitPane.getDividers().get(0).positionProperty();
		DoubleTransition dt = new DoubleTransition(Duration.millis(1000), doubleProperty);
		dt.setToValue(0); dt.play();
				
		FadeTransition ft = new FadeTransition(Duration.millis(1000), dimPane);
		ft.setFromValue(.45);
		ft.setToValue(0.0);
		ft.play();
				
		ft.setOnFinished(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				dimPane.setMouseTransparent(true);
				open = true;
			}
		});
	}
	
	@Override
	public void addFxml() {
						
		if(featureState == FeatureState.Search) {
			
			optionsAnchorPane.setVisible(true);
			
			largeLogo.setVisible(true);
			
			if(!optionsAnchorPane.getChildren().contains(searchFieldController)) {
				optionsAnchorPane.getChildren().add(searchFieldController);
			}
			
			if(!nutritionLabelContainer.getChildren().contains(nutritionLabelFrameController)) {
				nutritionLabelContainer.getChildren().add(nutritionLabelFrameController);
			}
			
			if(!itemListContainer.getChildren().contains(searchListFrameController)) {
				itemListContainer.getChildren().add(searchListFrameController);
			}
						
		} else if(featureState == FeatureState.FitTracker) {
			
			if(!itemListContainer.getChildren().contains(fitTrackerController)){
				itemListContainer.getChildren().add(fitTrackerController);
			}
			
		} else if(featureState == FeatureState.Macro) {
			
		} else if(featureState == FeatureState.Logout) {
			
		}
	}
	
	public void setLargeLogoImage(boolean visible) {
		
		if(visible == true) {
			largeLogo.setVisible(visible);	
		} else if(visible == false) {
			largeLogo.setVisible(visible);
		}
	}
	
	public void setNutritionLabel(double opacity, boolean visible) {
		
		nutritionLabelContainer.setVisible(visible);
		
		nutritionLabelDimPane.setVisible(visible);
		nutritionLabelDimPane.setOpacity(opacity);
	}
	
	@Override
	public void removeFxml() {
		
		optionsAnchorPane.setVisible(false);
		largeLogo.setVisible(false);
		itemListContainer.getChildren().clear();
	}
}

























