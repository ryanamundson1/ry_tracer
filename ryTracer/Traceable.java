package ryTracer;

import Tracer.HitRecord;
import vector.utilities.Point3d;
import vector.utilities.Vector3d;

public interface Traceable {
	public abstract Vector3d getVector(HitRecord hit);

}
