package bv.util.animation;

public class Point 
{
	private double x;
	private double y;
	
	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void setX(double x)
	{
		this.x = x;
	}
	
	public void setY(double y)
	{
		this.y = y;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public void addY(double y)
	{
		this.y += y;
	}
	
	public void addX(double x)
	{
		this.x += x;
	}
	
	/**
	 * Returns the difference between two points in a 2d coordinate plane using the Distance Forumula.
	 * @param a - 1st Point.
	 * @param b - 2nd Point.
	 * @return Returns the difference as a double
	 */
	public static double getDifference(Point a, Point b)
	{
		//  Takes the differences of the X's and Y's and squares the differences.  Then it adds the two squared numbers.
		//	Finally, it takes the added numbers and retrieves the square root of that number and returns it.
		return Math.pow(Math.pow(a.getY() - b.getY(), 2) + Math.pow(a.getX() - b.getX(), 2), 1.0 / 2.0);
		
	}
}
