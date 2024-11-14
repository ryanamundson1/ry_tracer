package meshes;

import java.util.ArrayList;

import vector.utilities.Point3d;
import vector.utilities.Vector3d;
import Tracer.HitRecord;

public class Poly extends Tri {
	
	public ArrayList<Point3d> points = new ArrayList<Point3d>();
	public ArrayList<Vector3d> normals = new ArrayList<Vector3d>();
	public ArrayList<double[]> uvs = new ArrayList<double[]>();
	Tri tri1;
	Tri tri2;
	
	public Poly(Point3d a,Point3d b,Point3d c,Point3d d, Vector3d na, Vector3d nb, Vector3d nc, Vector3d nd,
			double[] uva, double[] uvb, double[] uvc, double[] uvd){
		points.add(a);
		points.add(b);
		points.add(c);
		points.add(d);
		normals.add(na);
		normals.add(nb);
		normals.add(nc);
		normals.add(nd);
		uvs.add(uva);
		uvs.add(uvb);
		uvs.add(uvc);
		uvs.add(uvd);
		tri1 = new Tri(b,a,c, nb, na, nc, uvb, uva, uvc);
		tri2 = new Tri(b,c, d, nb, nc, nd, uvb, uvc , uvd);
	}
	
	public Poly(Point3d a,Point3d b,Point3d c,Point3d d, Vector3d na, Vector3d nb, Vector3d nc, Vector3d nd){
		points.add(a);
		points.add(b);
		points.add(c);
		points.add(d);
		normals.add(na);
		normals.add(nb);
		normals.add(nc);
		normals.add(nd);
		tri1 = new Tri(b,a,c, nb, na, nc);
		tri2 = new Tri(b,c, d, nb, nc, nd);
	}
	@Override
	public void printType() {
		System.out.println("Poly: points: a=" + points.get(0)+ " b=" + points.get(1)+ " c=" + points.get(2)+ " d=" + points.get(3));

	}

	public HitRecord hitTest(Point3d eye, Vector3d view, int bounces, double ior) {
		HitRecord hit;
		hit = tri1.hitTest(eye, view, bounces, ior);
		if (hit.distance > 0)
			return hit;
		else return tri2.hitTest(eye, view, bounces, ior);
	}

}
