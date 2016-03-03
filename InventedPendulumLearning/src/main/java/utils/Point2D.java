package utils;

public class Point2D {
	public double x;
	public double y;
	
	public double eucDist(Point2D p) {
		return Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2));
	}
}
