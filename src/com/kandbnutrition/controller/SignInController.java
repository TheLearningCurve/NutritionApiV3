package com.kandbnutrition.controller;

/*
 * Created 2/9/2016 by Kyle Wolff
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.kandbnutrition.resource.FeatureState;
import com.kandbnutrition.resource.StringValues;
import com.kandbnutrition.service.Authentication;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class SignInController extends AnchorPane implements Initializable {
	
	@FXML
	TextField emailField;
	
	@FXML
	PasswordField passwordField;
	
	@FXML
	VBox loginContainerVBox;
	
	@FXML
	Label errorLabel;
	
	@FXML 
	ImageView loadingIcon;
	
	@FXML
	Button signInButton, createAccountButton;
	
    public static SignInController controller;
    
	private MainFrameController mainFrameController;
	private StringValues stringValues;
	private Authentication authentication;
	
	private double erroLabelStandardHeight;
	
	public SignInController(MainFrameController mainFrameController) {
		
		stringValues = new StringValues();
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(stringValues.getSignInFxml()));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);
		controller = (SignInController) fxmlLoader.getController();
		
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		authentication = new Authentication();
		authentication.setMainController(mainFrameController, this);
		
		this.mainFrameController = mainFrameController;
		erroLabelStandardHeight = errorLabel.getPrefHeight();
		
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
				
				if(keyEvent.getCode() == KeyCode.ENTER) {
					login();
				}
			}
		});
		
		// Added this listener for the Enter Key. If the user presses Enter then can make the login call
		passwordField.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent keyEvent) {
				
				if(keyEvent.getCode() == KeyCode.ENTER) {
					login();
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
		
		signInButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				login();
			}
		});
		
		createAccountButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				mainFrameController.setFeatureState(FeatureState.CreateAccount);
				mainFrameController.addFxml();
				//authentication.createAccount("Alex", "Wolff", "alex@testing.com", "password1");
			}
		});
	}
	
	public void login() {
		loadingIconVisible(true);
		authentication.setUserInformation(emailField.getText(), passwordField.getText());
		authentication.authenticate();
	}
	
	public void adjustUI(int value) {
		loginContainerVBox.setLayoutY(loginContainerVBox.getLayoutY() + value);
	}
	
	public void clearPasswordField() {
		passwordField.clear();
	}
	
	/*
	 * Method to reset the login view
	 */
	
	public void resetLogin() {
		
		clearPasswordField();
		errorLabel.setText("");
		
		if(errorLabel.getPrefHeight() >= (erroLabelStandardHeight + 20)) {
			
			errorLabel.setPrefHeight(errorLabel.getHeight() - 20);
			adjustUI(-20);
		}
	}
	
	/*
	 * Method to set the error message UI when the authentication fails
	 */
	
	public void setErrorMessage(String errorMessage) {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				
				errorLabel.setVisible(true);
				
				if(errorMessage.length() > 19 && errorLabel.getPrefHeight() <= erroLabelStandardHeight) {
					
					errorLabel.setText(errorMessage);
					errorLabel.setPrefHeight(errorLabel.getHeight() + 20);
					adjustUI(20);
				} else {
					errorLabel.setText(errorMessage);
				}
			}
		});
	}
	
	public void loadingIconVisible(boolean visible) {
		loadingIcon.setVisible(visible);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
