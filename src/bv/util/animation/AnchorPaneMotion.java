package bv.util.animation;


import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * Allows simple one directional animation for AnchorPanes.  Useful for sliding effect of menus.
 * @author 	Brandon VanderMey
 *
 */
public class AnchorPaneMotion
{
	private Timeline timeline;
	private KeyFrame keyFrame;
	
	/**
	 * Recommended: 16.66
	 * <p>
	 * 16.66 - Equivalent to 60fps.
	 */
	private double keyFrameDuration;	//	Recommended: 16.66 (50/3) Is equivalent to vsync on 60hz monitor
	private double totalDuration;
	private int numberOfSteps;
	private Point startPoint;
	private Point endPoint;
	private List<Point> forwardSteps;
	private List<Point> backwardSteps;
	private List<Point> stepsToBePlayed;
	int index = 0;
	
	
	/**
	 * Constructor
	 * @param pane - AnchorPane to animate.
	 * @param totalDuration - Total animation duration in milliseconds.
	 * @param endPoint - X,Y coordinate to final position.
	 * @param equationType - What kind of motion you prefer.  Example: start slow then end fast (XSQARED), start fast then end slow (XINVERSESQUARE).
	 */
	
	public AnchorPaneMotion(AnchorPane pane, double totalDuration, Point endPoint, EquationType equationType, int fps)
	{
		this.totalDuration = totalDuration;
		keyFrameDuration = 1000.0 / fps;	//	Recommended: 16.66 (50/3).  Mirrors 60fps.  Milliseconds per frame (1000/60).
		this.endPoint = endPoint;
		
		numberOfSteps = (int)(totalDuration / keyFrameDuration);
		
		timeline = new Timeline();
		
		startPoint = new Point(pane.getLayoutX(), pane.getLayoutY());
		Steps steps = new Steps(totalDuration, keyFrameDuration, numberOfSteps, startPoint, endPoint, equationType);
		this.forwardSteps = steps.getForwardSteps();
		this.backwardSteps = steps.getBackwardSteps();
		
		keyFrame = new KeyFrame(Duration.millis(keyFrameDuration), new EventHandler<ActionEvent> () {
			
			
			
			@Override
			public void handle(ActionEvent arg0) 
			{
				pane.setLayoutX(stepsToBePlayed.get(index).getX());
				pane.setLayoutY(stepsToBePlayed.get(index).getY());
				
				index++;
				
				
			}
		});
		
		timeline.getKeyFrames().add(keyFrame);
		timeline.setCycleCount(numberOfSteps);		//	How many steps there will be before animation stops.
		
		//	Once animation has stopped, this method will execute.  It makes sure the anchor pane is at its final position.
		timeline.setOnFinished(new EventHandler<ActionEvent> () {

			@Override
			public void handle(ActionEvent arg0) 
			{
				pane.setLayoutX(endPoint.getX());
				pane.setLayoutY(endPoint.getY());
				
				reset();
			}
		});
		
		
				
	}
	
	public AnchorPaneMotion(AnchorPane pane, double totalDuration, Point endPoint, EquationType equationType)
	{
		this(pane, totalDuration, endPoint, equationType, 60);
	}
	
	/**
	 * Plays the animation.
	 */
	public void play()
	{
		stepsToBePlayed = forwardSteps;
		timeline.play();
	}
	
	/**
	 * Reverses the animation.  Essentially, the inverse of the steps used.  For example, step 1 in ForwardSteps
	 * will be the last step in BackwardsSteps.
	 */
	public void reverse()
	{
		stepsToBePlayed = backwardSteps;
		timeline.play();
	}
	
	public void reset()
	{
		index = 0;
		
	}
	
	public void setDuration(double duration)
	{
		this.totalDuration = duration;
	}
	
	public void setKeyFrameDuration(double keyFrameDuration)
	{
		this.keyFrameDuration = keyFrameDuration;
	}
	
	public void setToPoint(Point toPoint)
	{
		this.endPoint = toPoint;
	}
	
	public double getDuration()
	{
		return totalDuration;
	}
	
	public double getKeyFrameDuration()
	{
		return keyFrameDuration;
	}
	
	public Point getToPoint()
	{
		return endPoint;
	}
	
	public List<Point> getFinalSteps()
	{
		return forwardSteps;
	}
}
