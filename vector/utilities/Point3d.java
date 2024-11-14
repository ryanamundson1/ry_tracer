package vector.utilities;

public class Point3d {
	public double x,y,z;
	public Point3d(double a, double b, double c){
		x = a;
		y = b;
		z = c;
		}
	
	public void normalize(){
		}
	
	public String toString(){
		return ("x: " + x + " y: " + y + " z: " +z);
		}
	}
