package meshes;

import Tracer.HitRecord;
import vector.utilities.Point3d;
import vector.utilities.Vector3d;

public interface Hittable {
	
	//abstract HitRecord hitTest(Point3d eye, Vector3d view, int i);
	abstract HitRecord hitTest(Point3d eye, Vector3d view, int i, double ior);

}