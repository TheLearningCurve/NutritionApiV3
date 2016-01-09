package com.kandbnutrition.handler;

import javax.management.DescriptorKey;

import com.kandbnutrition.controller.MainFrameController;

import javafx.scene.Scene;
import javafx.stage.Stage;

public enum SceneManager {
	INSTANCE;
	
	public static SceneManager sceneManager = null;
	private Stage primaryStage;
	private MainFrameController mainFrameController;
	private Scene mainScene;
	
//	public static SceneManager getInstance() {
//		
//		if(sceneManager == null){
//			sceneManager = new SceneManager();
//		}
//		
//		return sceneManager;
//	}
	
	public void setStage(Stage stage){
		primaryStage = stage;
	}
	
	public void instantiateControllers(){
		mainFrameController = new MainFrameController();
		
		mainScene = new Scene(mainFrameController);
	}
	
	public MainFrameController getMainFrameController(){
		return mainFrameController;
	}
	
	@DescriptorKey(value = "Need a string. Either (Main) or (SignOn).")
	public void setScene(String scene){
		if(scene.compareTo("Main") == 0){
			primaryStage.setScene(mainScene);
			primaryStage.centerOnScreen();
			primaryStage.show();
		}
	}
}
