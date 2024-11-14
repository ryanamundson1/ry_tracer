package vector.utilities;

public class Vector3d extends Point3d {
	public Vector3d(double a, double b, double c){
		super(a,b,c);
		}
	public void normalize(){
		double a = this.length();
		if (a != 0){
			x/=a; 
			y/=a; 
			z/=a;
			}
		}
	public double length(){
		return Math.sqrt(x*x + y*y + z*z);
		}
	public double norm(){
		return x*x + y*y + z*z;
		}
	public Vector3d clone(){
		return new Vector3d(x, y, z);
	}
	public String toString(){
		return ("x: " + x + " y: " + y + " z: " +z);
		}
	public Vector3d subtract(Vector3d b) {
		return new Vector3d (x-b.x, y-b.y, z-b.z);
		}
	public Vector3d add(Vector3d b) {
		return new Vector3d (x+b.x, y+b.y, z+b.z);
		}
	}
	