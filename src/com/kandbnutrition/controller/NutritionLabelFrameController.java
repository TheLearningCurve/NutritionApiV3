package com.kandbnutrition.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.kandbnutrition.nutritionLabelHTML.HTMLBuilder;
import com.kandbnutrition.resource.StringValues;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class NutritionLabelFrameController extends AnchorPane implements Initializable {
	@FXML
	AnchorPane rightPanel;
	
	@FXML
	WebView webViewControl;
	
	@FXML
	Button close_icon;
	
	private WebEngine engine;
	private StringValues stringValues;
	public SearchListFrameController searchList;
	public MainFrameController mainFrameController;
	
	public static NutritionLabelFrameController controller;

	public NutritionLabelFrameController(MainFrameController mainFrameController)
	{
		stringValues = new StringValues();
		this.mainFrameController = mainFrameController;
				
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(stringValues.getNutritionLabelFxml()));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);
		controller = (NutritionLabelFrameController) fxmlLoader.getController();
		
		try
		{
			fxmlLoader.load();
		}
		
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}	
		
		engine = webViewControl.getEngine();	
		
		close_icon.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
				mainFrameController.setNutritionLabel(0, false);
			}
		});
	}
	
	public void sendHtml(HTMLBuilder html)
	{
		engine.loadContent(html.getHTMLString());
	}
	
	public void setSearchListFrameController(SearchListFrameController searchList) {
		this.searchList = searchList;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		
	}
}
