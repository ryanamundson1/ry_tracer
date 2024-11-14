package meshes;

import ryTracer.Color;
import texture.UVMapper;
import Tracer.HitRecord;
import Tracer.Tracer;
import vector.utilities.Point3d;
import vector.utilities.Vector3d;
import vector.utilities.VectorMath;

public class Sphere extends Mesh {
	double radius;
	Point3d location;
	

	public Sphere(double rad, Point3d loc, Color col, long mId) {
		radius = rad;
		location = loc;
		color = col;
		matId = mId;
	}

	public void printType() {
		System.out.println("Sphere: radius=" + radius +" Location="+location+ " Color=" +color);
	}

	public HitRecord hitTest(Point3d eye, Vector3d view, int bounces, double ior) {
		
		Vector3d tmp = VectorMath.subtract( location, eye);
		//tmp.normalize();
		double dA = view.norm();
		//dA *= dA;
		double dB = -2*VectorMath.dot(tmp, view);
		double dC = tmp.norm();
		//dC *= dC;
		dC -= (radius*radius);
		
		double dtrm = (dB*dB)-(4*dA*dC);
		if (dtrm < 0) return new HitRecord(-1, null, null, view, null, -1 ,-1, -1, -1); //here's that determinate
		
		dtrm = Math.sqrt(dtrm);
		if(ior != Tracer.AIR_IOR){
			double dist = (-dB+dtrm)/2*dA;
			Vector3d tmp2 = VectorMath.multiply(dist, view);
			Point3d hitPoint = VectorMath.add(tmp2, eye);
			Vector3d norm = VectorMath.subtract( hitPoint, location);
			norm.normalize();
			if( map != null){
				double[] uvs = map.getUVs(norm , new Vector3d(0,0,1), radius);
				//System.out.println("u: " + uvs[0] +"v: "+ uvs[1] );
				return new HitRecord (dist, hitPoint, norm, view, this, uvs[0], uvs[1], bounces, Tracer.AIR_IOR);
			}
			return new HitRecord (dist, hitPoint, norm, view, this, 0, 0, bounces, ior);
			
		}
		double dist = (-dB-dtrm)/2*dA;
		Vector3d tmp2 = VectorMath.multiply(dist, view);
		Point3d hitPoint = VectorMath.add(tmp2, eye);
		Vector3d norm = VectorMath.subtract( hitPoint, location);
		norm.normalize();
		if( map != null){
			double[] uvs = map.getUVs(norm , new Vector3d(0,0,1), radius);
			//System.out.println("u: " + uvs[0] +"v: "+ uvs[1] );
			return new HitRecord (dist, hitPoint, norm, view, this, uvs[0], uvs[1], bounces, Tracer.AIR_IOR);
		}
		return new HitRecord (dist, hitPoint, norm, view, this, 0, 0, bounces, Tracer.AIR_IOR);
		//return VectorMath.multiply(t1, view).lenght(); //return the distance
	}

}
