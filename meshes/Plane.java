package meshes;

import ryTracer.Color;
import Tracer.HitRecord;
import Tracer.Tracer;
import vector.utilities.Point3d;
import vector.utilities.Vector3d;
import vector.utilities.VectorMath;

public class Plane extends Mesh{
	Vector3d normal;
	Point3d origin;
	double d;
	
	public Plane(){
	}

	public Plane(Color c, Vector3d norm, Point3d o, long mId) {
		color = c;
		normal = norm;
		origin = o;
		matId = mId;
		d = VectorMath.subtract(origin, new Point3d(0,0,0)).length();
	}

	public void printType() {
		System.out.println("Plane: Normal=" + normal + " origin= " +origin+ " color= "+ color);
	}

	public HitRecord hitTest(Point3d eye, Vector3d view, int bounces, double ior) {
		
		//t = n.(o-q)/n.d
		//If the denominator is zero, the ray is parallel to the plane. If the numerator is zero, the ray starts from the plane. 
		//If both denominator and the numerator are zero, the ray is contained in the plane. 
		//Vector3d tmp = VectorMath.subtract( eye, );
		//tmp.normalize();
		double num = VectorMath.dot(normal, eye) + d;
		if (num == 0) return new HitRecord(-1, null, null, view, null, -1, -1, -1, -1);
		double denom = VectorMath.dot(normal, view);
		if (denom == 0) return new HitRecord(-1, null, null, view, null, -1, -1, -1, -1 );
		else{
			double dist =-num/denom;
			if( dist < 0) return new HitRecord(-1, null, null, view, null, -1, -1, -1, -1);
			//dist = Math.sqrt(dist);
			Vector3d tmp2 = VectorMath.multiply(dist, view);
			Point3d hitPoint = VectorMath.add(tmp2, eye);
			Vector3d norm = normal.clone();
			//norm = VectorMath.invert(norm);
			norm.normalize();
			if( map != null){
				double[] uvs = map.getUVs(hitPoint , normal, 0);
				//System.out.println("u: " + uvs[0] +"v: "+ uvs[1] );
				return new HitRecord (dist, hitPoint, norm, view, this, uvs[0], uvs[1], bounces, Tracer.AIR_IOR);
			}
			return new HitRecord (dist, hitPoint, norm, view, this, 0, 0, bounces, Tracer.AIR_IOR);
		}
	}

}