package application;
	
import com.kandbnutrition.handler.SceneManager;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	
	private SceneManager sceneManager = SceneManager.INSTANCE;
	
	@Override
	public void start(Stage primaryStage) {
		
		primaryStage.setResizable(false);
		
		sceneManager.setStage(primaryStage);
		sceneManager.instantiateControllers();
		sceneManager.setScene("Main");
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
