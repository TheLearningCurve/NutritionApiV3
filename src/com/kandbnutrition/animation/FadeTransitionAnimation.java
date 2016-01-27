package com.kandbnutrition.animation;

/*
 * Created by Kyle Wolff 1/26/2016 
 * 
 * The FadeTransitionAnimation was made to utilize the DoubleTransition class multiple times.
 */

import javafx.animation.FadeTransition;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class FadeTransitionAnimation {
	
	private DoubleTransition dt;
	private FadeTransition ft;
	private ScrollPane scrollPane;
	private Pane pane;
		
	public FadeTransitionAnimation() {
		
	}
	
	public void setScrollPane(ScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}
	
	public void setPane(Pane pane) {
		this.pane = pane;
	}
	
	public void setDuration(DoubleProperty doubleProperty, double toValue, double seconds) {
		
		dt = new DoubleTransition(Duration.seconds(seconds), doubleProperty); 
		dt.setToValue(toValue);
	}
	
	public void playDuration() {
		dt.play();
	}
	
	public void setFadeTransition(Node n, double millis, double delayedMillis, double fromValue, double toValue, boolean visible, boolean transparent ) {
		
		ft = new FadeTransition(Duration.millis(millis), n);
		ft.delayProperty().set(Duration.millis(delayedMillis));
		ft.setFromValue(fromValue);
		ft.setToValue(toValue);
		
		ft.setOnFinished(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						
						if(n == pane) {
							pane.setMouseTransparent(transparent);
						} else if(n == scrollPane) {
							scrollPane.setVisible(visible);
						}
					}
				});	
	}
	
	public void playFadeTransition() {
		ft.play();
	}	
}
