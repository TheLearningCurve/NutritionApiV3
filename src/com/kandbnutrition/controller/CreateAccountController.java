package com.kandbnutrition.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class CreateAccountController extends AnchorPane implements Initializable
{
	@FXML
	Button btnSubmit;
	
	@FXML
	Button btnCancel;
	
	public CreateAccountController()
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("com/kandbnutrition/views/CreateAccount.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		
		try
		{
			fxmlLoader.load();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		btnSubmit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent> () {

			@Override
			public void handle(MouseEvent event) 
			{
				
			}
			
		});
		
		btnCancel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent> () {

			@Override
			public void handle(MouseEvent event) 
			{
				
			}
			
		});
	}

}
