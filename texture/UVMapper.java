package texture;

import vector.utilities.Point3d;
import vector.utilities.Vector3d;

public abstract class UVMapper extends Texture {
	String type;

	public abstract double[] getUVs(Point3d pt, Vector3d point, double d); 
}
