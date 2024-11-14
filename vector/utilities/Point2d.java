package vector.utilities;


public class Point2d {
	public double x,y;
	public Point2d(double a, double b){
		x = a;
		y = b;
		}
	
	public void normalize(){
		}
	
	public String toString(){
		return ("x: " + x + " y: " + y);
		}

	public void mult(double d) {
		x*=d;
		y*=d;
		}
	
	public void add(Point2d d) {
		x*=d.x;
		y*=d.y;
		}
	}
