package vector.utilities;

public final class VectorMath{
	
	static public Vector3d cross (Vector3d a, Vector3d b){
		return new Vector3d (a.y*b.z - a.z*b.y, a.z*b.x - a.x*b.z, a.x*b.y - a.y*b.x);
	}
	static public double dot (Vector3d a, Vector3d b){
		return (a.x*b.x + a.y* b.y + a.z * b.z);
	}
	static public Vector3d add (Vector3d a, Vector3d b){
		return new Vector3d(a.x+b.x,a.y+b.y,a.z+b.z );
	}
	static public Point3d add (Vector3d a, Point3d b){
		return new Point3d(a.x+b.x,a.y+b.y,a.z+b.z );
	}
	static public Vector3d subtract (Vector3d a, Vector3d b){
		return new Vector3d(a.x-b.x,a.y-b.y,a.z-b.z );
	}
	static public Point3d subtract (Vector3d a, Point3d b){
		return new Point3d(a.x-b.x,a.y-b.y,a.z-b.z );
	}
	static public Vector3d multiply (Vector3d a, Point3d b){
		return new Vector3d(a.x*b.x,a.y*b.y,a.z*b.z );
	}
	static public Vector3d invert (Vector3d a){
		return new Vector3d(-a.x,-a.y,-a.z );
	}
	public static Vector3d subtract(Point3d a, Point3d b) {
		return new Vector3d(a.x-b.x,a.y-b.y,a.z-b.z );
	}
	public static Vector3d multiply(double d, Vector3d a) {
		// TODO Auto-generated method stub
		return new Vector3d (a.x*d, a.y*d, a.z*d);
	}
	public static Point3d add(Point3d a, Point3d b) {
		return new Point3d(a.x+b.x,a.y+b.y,a.z+b.z );
	}
	public static Vector3d multiply(Point3d a, Vector3d b) {
		return new Vector3d(a.x*b.x,a.y*b.y,a.z*b.z );
	}
	public static double dot(Vector3d a, Point3d b) {
		return (a.x*b.x + a.y* b.y + a.z * b.z);
	}
}
