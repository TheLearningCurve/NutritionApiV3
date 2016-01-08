package com.kandbnutrition.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.kandbnutrition.resource.StringValues;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class SingleLabelController extends AnchorPane implements Initializable {
	
	public StringValues stringValues;
	
	@FXML
	Label label;
	
	public SingleLabelController(String x)
	{
		stringValues = new StringValues();
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(stringValues.getSingleCellFxml()));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);
		
		try
		{
			fxmlLoader.load();
		}
		
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		
		label.setText(x);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}

}
