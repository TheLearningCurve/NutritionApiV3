package com.kandbnutrition.controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.kandbnutrition.animation.FadeTransitionAnimation;
import com.kandbnutrition.handler.FxmlHandler;
import com.kandbnutrition.resource.FeatureState;
import com.kandbnutrition.resource.StringValues;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class MainFrameController extends AnchorPane implements Initializable, FxmlHandler {		
	
	@FXML
	Pane dimPane, mainFrameTopContainer, mainRightAnchorPane, mainPaneContainer,
		optionsAnchorPane,nutritionLabelDimPane,nutritionLabelContainer, loginPaneContainer;
	@FXML
	SplitPane navMenuSplitPane;
	
	@FXML
	ScrollPane loginScrollPane;
	
	@FXML
	AnchorPane navMenu, loginAnchorPane;
	
	@FXML
	ProgressIndicator ItemListProgressIndicator;
	
	@FXML
	ImageView largeLogo;
	
	@FXML
	Button navMenuButton, signInButton, createAccountButton;
	
	@FXML
	TextField emailField;
	
	@FXML
	PasswordField passwordField;
	
	@FXML
	HBox searchIconHBox, macroCalculatorHBox, fitTrackerHBox, logoutHBox, itemListContainer;
	
	private StringValues stringValues;
	private SearchFieldController searchFieldController;
	private FitTrackerController fitTrackerController;
	public SearchListFrameController searchListFrameController;	
	public NutritionLabelFrameController nutritionLabelFrameController;
	private FadeTransitionAnimation fadeTransitionAnimation;
	
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
		fadeTransitionAnimation = new FadeTransitionAnimation();
		
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
		
		/*
		 * Setting the transition objects for validation checks 
		 */
		
		fadeTransitionAnimation.setScrollPane(loginScrollPane);
		fadeTransitionAnimation.setPane(dimPane);
	
		navMenuButton.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
									
				if(open == true){
					openMenu();
					searchFieldController.setTextFieldDisabled(true, 0);
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
				
				loginScrollPane.setVisible(true);
				fadeTransitionAnimation.setDuration(loginScrollPane.hvalueProperty(), -50.0, 20);
				fadeTransitionAnimation.playDuration();
				
				fadeTransitionAnimation.setFadeTransition(loginScrollPane, 1000, 0, 0.0, 1, true, false);
				fadeTransitionAnimation.playFadeTransition();
			}
		});
		
		/* When the user clicks through the tab field starting at the Email field it would
		 * go in a loop from password field to create account button and never touch the email field. 
		 * 
		 * This listener is to make the loop go back to the email field. 
		 */
		
		emailField.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent keyEvent) {
				
				if(keyEvent.getCode() == KeyCode.TAB) {
					//Sets the Password field to focused 
					passwordField.setFocusTraversable(true);
				}
			}
		});

		createAccountButton.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent keyEvent) {
				
				if(keyEvent.getCode() == KeyCode.TAB) {
					//Sets the Email field to focused 
					emailField.setFocusTraversable(true);
				}
			}
		});
		
		// Added this listener for the Enter Key. If the user presses Enter then can make the login call
		passwordField.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent keyEvent) {
				
				if(keyEvent.getCode() == KeyCode.ENTER) {
					
				}
			}
		});
		
		signInButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
												
				fadeTransitionAnimation.setDuration(loginScrollPane.hvalueProperty(), 50.0, 15);
				fadeTransitionAnimation.playDuration();
				
				fadeTransitionAnimation.setFadeTransition(loginScrollPane, 1000, 500, 1, 0.0, false, true);
				fadeTransitionAnimation.playFadeTransition();
			}
		});
		
		createAccountButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
			}
		});
	}
	
	public void openMenu() {	
		
		fadeTransitionAnimation.setDuration(navMenuSplitPane.getDividers().get(0).positionProperty(), .19, 1);
		fadeTransitionAnimation.playDuration();
		
		fadeTransitionAnimation.setFadeTransition(dimPane, 1000, 0, 0.0, .45, false, false);
		fadeTransitionAnimation.playFadeTransition();
		
		open = false;
	}
	
	public void closeMenu() {			
				
		fadeTransitionAnimation.setDuration(navMenuSplitPane.getDividers().get(0).positionProperty(), 0, 1);
		fadeTransitionAnimation.playDuration();
		
		fadeTransitionAnimation.setFadeTransition(dimPane, 1000, 0, .45, 0.0, false, true);
		fadeTransitionAnimation.playFadeTransition();
		
		searchFieldController.setTextFieldDisabled(false, 1);
				
		open = true;
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
	
	public boolean getMenuState() {
		return open;
	}
	
	@Override
	public void removeFxml() {
		
		optionsAnchorPane.setVisible(false);
		largeLogo.setVisible(false);
		itemListContainer.getChildren().clear();
		searchListFrameController.reset();
	}
}

























