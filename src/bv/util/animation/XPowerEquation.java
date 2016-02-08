package bv.util.animation;


/**
 * This class aids in the animation of the steps taken in a given time frame.  An example of this would be 
 * is if you wanted a menu to start out moving fast at first and then move slower as it reaches its destination.
 * <p>
 * Visually it is more pleasing to vary its velocity slightly.  Otherwise, it is just a linear motion. 
 * @author Brandon VanderMey
 */
public class XPowerEquation 
{
	private double power;
	
	/**
	 * Constructor.  Equation: (y = x ^ power)
	 * @param x - Time in milliseconds.
	 * @param power 
	 */
	public XPowerEquation(double power)
	{
		this.power = power;
	}
	
	public double getY(double x)
	{
		return Math.pow(x, power);
	}
	
}
