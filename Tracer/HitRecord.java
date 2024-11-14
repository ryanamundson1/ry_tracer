package Tracer;

import meshes.Mesh;
import vector.utilities.Point3d;
import vector.utilities.Vector3d;

public class HitRecord {
	
	public Vector3d normal = null;
	public Vector3d view = null;
	public Point3d hitPoint = null;
	public double distance;
	public Mesh object;
	public double u;
	public double v;
	public int bounces;
	public double ior;
	
	public HitRecord(double d, Point3d p, Vector3d n, Vector3d vec, Mesh obj , double du , double dv, int bounce, double ir) {
		distance = d;
		hitPoint = p;
		normal = n;
		view = vec;
		object = obj;
		u = du;
		v = dv;
		bounces = bounce;
		ior = ir;
	}
	
}
