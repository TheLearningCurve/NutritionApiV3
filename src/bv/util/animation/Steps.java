package bv.util.animation;

import java.util.ArrayList;
import java.util.List;

public class Steps 
{
	private List<Point> steps;
	private XPowerEquation equation;
	
	private double yDifference;	// Difference between steps x-axis.
	private double xDifference; // Difference between steps y-axis.
	private double currentDuration = 0.0;
	private double addToCoordinateRatio = 0.0;
	private double addToXCoordinate = 0.0;
	private double addToYCoordinate = 0.0;
	
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
		
		steps = new ArrayList<Point> ();
		
		xDifference = endPoint.getX() - startPoint.getX();
		yDifference = endPoint.getY() - startPoint.getY();
		
		for (int i = 0; i < numberOfCycles; i++)
		{
			currentDuration += keyFrameDuration;
			addToCoordinateRatio = equation.getY(currentDuration / totalDuration);
			addToXCoordinate = addToCoordinateRatio * xDifference;
			addToYCoordinate = addToCoordinateRatio * yDifference;
			
			steps.add(new Point(addToXCoordinate, addToYCoordinate));
			
			//System.out.println(currentPoint.getX() + " " + currentPoint.getY());
		}
	}
	
	public List<Point> getSteps()
	{
		return steps;
	}
}
