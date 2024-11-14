package meshes;

import java.util.ArrayList;

import vector.utilities.Point2d;
import vector.utilities.Point3d;
import vector.utilities.Vector3d;
import vector.utilities.VectorMath;
import Tracer.HitRecord;
import Tracer.Tracer;

public class Tri extends Mesh{
	public ArrayList<Point3d> points = new ArrayList<Point3d>();
	public ArrayList<Vector3d> normals = new ArrayList<Vector3d>();
	public ArrayList<Point2d> pointuv = new ArrayList<Point2d>();
	boolean hasUVS = false;
	boolean hasNormals = false;
	
	public Tri(){
		
	}
	
	public Tri(Point3d a, Point3d b,  Point3d c){
		points.add(a);
		points.add(b);
		points.add(c);
	}
	
	public Tri(Point3d a, Point3d b,  Point3d c, Vector3d na, Vector3d nb,  Vector3d nc ){
		points.add(a);
		points.add(b);
		points.add(c);
		normals.add(na);
		normals.add(nb);
		normals.add(nc);
		hasNormals = true;
	}
	
	public Tri(Point3d a, Point3d b, Point3d c, Vector3d na, Vector3d nb, Vector3d nc, double[] uva, double[] uvb, double[] uvc) {
		points.add(a);
		points.add(b);
		points.add(c);
		normals.add(na);
		normals.add(nb);
		normals.add(nc);
		pointuv.add(new Point2d(uva[0], uva[1]));
		pointuv.add(new Point2d(uvb[0], uvb[1]));
		pointuv.add(new Point2d(uvc[0], uvc[1]));
		hasUVS = true;
		hasNormals = true;
	}

	public void printType() {
		
	}

	public HitRecord hitTest(Point3d eye, Vector3d view, int bounces, double ior) {
		Vector3d edge1, edge2, tvec, pvec, qvec;
		double det, inv, dist;
		double u, v;
		edge1 = VectorMath.subtract(points.get(1), points.get(0));
		edge2 = VectorMath.subtract(points.get(2), points.get(0));
		pvec = VectorMath.cross(view, edge2);
		det = VectorMath.dot(edge1, pvec);
		
		//
		if (det > MINIMUM){
			tvec = VectorMath.subtract(eye, points.get(0));
			u = VectorMath.dot(tvec, pvec);
			if(u < 0 || u > det){
				return new HitRecord(-1, null, null, view, null, -1, -1, -1, -1);
			}
			qvec = VectorMath.cross(tvec, edge1);
			
			v = VectorMath.dot(view, qvec);
			
			if (v < 0 || (u + v) > det){
				return new HitRecord(-1, null, null, view, null, -1, -1, -1, -1);
			}
		}
		else if (det < -MINIMUM){
			tvec = VectorMath.subtract(eye, points.get(0));
			u = VectorMath.dot(tvec, pvec);
			if(u > 0 || u < det){
				return new HitRecord(-1, null, null, view, null, -1, -1, -1, -1);
			}
			qvec = VectorMath.cross(tvec, edge1);
			
			v = VectorMath.dot(view, qvec);
			
			if (v > 0 || (u + v) < det){
				return new HitRecord(-1, null, null, view, null, -1, -1, -1, -1);
			}
		}
		else return new HitRecord(-1, null, null, view, null, -1, -1, -1, -1);
			
			inv = 1/det;
			dist = inv * VectorMath.dot(edge2, qvec);
			u *= inv;
			v *= inv;
			Vector3d normal;
			if (hasNormals){
				//N = N1 + m_U * (N2 - N1) + m_V * (N3 - N1);
				normal = normals.get(0).add(VectorMath.multiply(u, normals.get(1).subtract(normals.get(0))));
				Vector3d normal2 = VectorMath.multiply(v, VectorMath.subtract(normals.get(2), normals.get(0)));
				normal.add(normal2).normalize();
			}
			else{
				normal = qvec;
				normal.normalize();
			}
			
			if (hasUVS){
				double tempu = pointuv.get(0).x;
				double tempv = pointuv.get(0).y;
				u = tempu + u*pointuv.get(1).x;
				v = tempv + v*pointuv.get(1).y;
			}
			
			Vector3d tmp2 = VectorMath.multiply(dist, view);
			Point3d hitPoint = VectorMath.add(tmp2, eye);
			return new HitRecord (dist, hitPoint, normal, view, this, u, v, bounces, Tracer.AIR_IOR);
	}

}
