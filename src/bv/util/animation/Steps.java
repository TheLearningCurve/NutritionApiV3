package bv.util.animation;

import java.util.ArrayList;
import java.util.List;

public class Steps 
{
	private List<Point> forwardSteps;
	private List<Point> backwardSteps;
	private XPowerEquation equation;
	
	private double yDifference;	// Difference between steps x-axis.
	private double xDifference; // Difference between steps y-axis.
	private double currentDuration = 0.0;
	private double addToCoordinateRatio = 0.0;
	private double addToXCoordinate = 0.0;
	private double addToYCoordinate = 0.0;
	
	private int numberOfSteps;
	
	public Steps(double totalDuration, double keyFrameDuration, int numberOfCycles, Point startPoint, Point endPoint, EquationType equationType)
	{
		if (equationType == EquationType.XSQUARED)
			equation = new XPowerEquation(2.0);
		
		else if (equationType == EquationType.XINVERSESQUARED)
			equation = new XPowerEquation((1.0 / 2.0));
		
		else if (equationType == EquationType.XCUBED)
			equation = new XPowerEquation(3.0);
		
		else if (equationType == EquationType.XINVERSECUBED)
			equation = new XPowerEquation((1.0 / 3.0));
		
		forwardSteps = new ArrayList<Point> ();
		numberOfSteps = numberOfCycles;
		
		xDifference = endPoint.getX() - startPoint.getX();
		yDifference = endPoint.getY() - startPoint.getY();
		
		for (int i = 0; i < numberOfCycles; i++)
		{
			currentDuration += keyFrameDuration;
			addToCoordinateRatio = equation.getY(currentDuration / totalDuration);
			addToXCoordinate = addToCoordinateRatio * xDifference;
			addToYCoordinate = addToCoordinateRatio * yDifference;
			
			forwardSteps.add(new Point(addToXCoordinate, addToYCoordinate));
			
			//System.out.println(currentPoint.getX() + " " + currentPoint.getY());
		}
		
		
		
		for(int i = numberOfSteps; i >= 0; i--)
		{
			backwardSteps.add(new Point(forwardSteps.get(i).getX(), forwardSteps.get(i).getY()));
		}
	}
	
	public List<Point> getForwardSteps()
	{
		return forwardSteps;
	}
	
	public List<Point> getBackwardSteps()
	{
		return backwardSteps;
	}
}
